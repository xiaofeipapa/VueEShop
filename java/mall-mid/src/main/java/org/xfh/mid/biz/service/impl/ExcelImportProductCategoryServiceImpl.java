package org.xfh.mid.biz.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.mid.biz.service.IExcelImportProductCategoryService;
import org.xfh.mid.db.po.ProductCategory;
import org.xfh.mid.vo.ExcelImportResult;

public class ExcelImportProductCategoryServiceImpl implements IExcelImportProductCategoryService{

	private Logger logger = LoggerFactory.getLogger(ExcelImportProductCategoryServiceImpl.class);
	
	@Autowired
	CommonDao commonDao;
	
	String tableName = "ProductCategory";
	

	// 获取cell的值, 去除null的情况
	private String getCellValue(HSSFSheet sheet, int rowIndex, int cellIndex) {
		String value = sheet.getRow(rowIndex).getCell(0).getStringCellValue();	
		if(CheckUtils.isEmpty(value))return "";
		
		return value.toString();
	}
	
	
	@Override
	public ExcelImportResult doImport(InputStream in) throws LogicException{
		
		/**
		 * 总体思路: 
		 * 
		 * 1) 如果是1级数据, 只要名字相同就是重复. 
		 * 2) 如果是2级数据, 只要 根分类_父分类 相同就是重复
		 * 3) 如果是3级数据, 只要 根分类_父分类_子分类 相同就是重复
		 * 
		 * 所以大部分力气都在将excel数据和db数据 拼成如上结构. 
		 * 
		 */
		
		CheckExcelResult checkExcelResult = this.checkExcelValid(in);
		
		Map<String, TempData> excelMap = checkExcelResult.nameMap;
		List<String> excelErrors = checkExcelResult.errors;

		// 获取所有的分类
		Map<String, String> dbDataMap = this.getAllDbData();
		
		List<String> dbErrors = new ArrayList<>();
		
		// 思路, 将各级数据和原数据库比对. 
		Map<String, String> toInserMap = new HashMap<>();
		for(String key : excelMap.keySet()) {

			TempData data = excelMap.get(key);
			
			if(dbDataMap.containsKey(key)) {
				// 与db 数据重复
				String error = String.format("第%d行的%d级数据[%s]和数据库已有数据重复", data.rowIndex, data.level, data.name);
				dbErrors.add(error);
			}
			else {
				// 将 root_parent_name 的结构反过来保存, 以备插入的时候获得parentId
				toInserMap.put(data.name, key);
			}
			
		}
		
		int insertCount = 0;
		
		// 插入1级数据
		String filterSql = "and level=1";
		int maxOrder = commonDao.getMaxBySql(this.tableName, "order", filterSql);
		
		Map<String, Long> rootNameIdMap = new HashMap<>();		// 保存插入后的根id
		for(String value : toInserMap.values()) {
			if(value.contains("_")) {
				continue;
			}
			
			ProductCategory data = new ProductCategory();
			data.setLevel(ProductCategory.LEVEL_ROOT);
			data.setShowFront(ProductCategory.SHOW_FRONT_NO);
			data.setName(value);
			maxOrder += 1;
			data.setOrder(maxOrder);
			
			TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(data, false, null, null);
			commonDao.insertOne(tableName, dataMap);
			
			Long id = Long.valueOf(dataMap.get("id").toString());
			rootNameIdMap.put(value, id);			
			
			insertCount++;
		}
		
		// 插入2级数据
		Map<String, Long> parentNameIdMap = new HashMap<>();		// 保存插入后的父id
		
		for(String value : toInserMap.values()) {
			if( ! value.contains("_")) {
				// 1级分类已经插入完毕
				continue;
			}
			
			String[] vlist = value.split("_");
			if(vlist.length == 2) {
				String rootName = vlist[0];
				String parentName = vlist[1];
				
				Long parentId = rootNameIdMap.get(rootName);

				// 这个查询只能放在循环里了
				String pSql = "and level=2 and parentId=" + parentId;				
				int parentOrder = commonDao.getMaxBySql(this.tableName, "order", pSql);
				
				ProductCategory data = new ProductCategory();
				data.setLevel(ProductCategory.LEVEL_PARENT);
				data.setShowFront(ProductCategory.SHOW_FRONT_NO);
				data.setName(parentName);
				data.setParentId(parentId);

				data.setOrder(parentOrder + 1);
				
				TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(data, false, null, null);
				commonDao.insertOne(tableName, dataMap);
				
				Long id = Long.valueOf(dataMap.get("id").toString());
				parentNameIdMap.put(value, id);		
				
				insertCount++;
				
			}
			
		}

		
		// 插入3级数据
		
		for(String value : toInserMap.values()) {
			if( ! value.contains("_")) {
				// 1级分类已经插入完毕
				continue;
			}
			
			String[] vlist = value.split("_");
			if(vlist.length == 3) {
				String rootName = vlist[0];
				String parentName = vlist[1];
				String name = vlist[2];
				
				Long parentId = parentNameIdMap.get(rootName + "_" + parentName);

				// 这个查询只能放在循环里了
				String pSql = "and level=3 and parentId=" + parentId;				
				int parentOrder = commonDao.getMaxBySql(this.tableName, "order", pSql);
				
				ProductCategory data = new ProductCategory();
				data.setLevel(ProductCategory.LEVEL_CHILD);
				data.setShowFront(ProductCategory.SHOW_FRONT_NO);
				data.setName(name);
				data.setParentId(parentId);

				data.setOrder(parentOrder + 1);
				
				TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(data, false, null, null);
				commonDao.insertOne(tableName, dataMap);

				insertCount++;
								
			}
			
		}
		
		ExcelImportResult importResult = new ExcelImportResult();
		importResult.setExcelErrors(excelErrors);
		importResult.setDbErrors(dbErrors);
		importResult.setInsertCount(insertCount);
		
		return importResult;
	}
	
	// 先检查excel本身数据是否合法
	private CheckExcelResult checkExcelValid(InputStream in) throws LogicException {
		
		Map<String, TempData> nameMap = new HashMap<>();		// 用于暂存数据		
		List<String> errors = new ArrayList<>();
		CheckExcelResult checkResult = new CheckExcelResult();
		checkResult.nameMap = nameMap;
		checkResult.errors = errors;

		String currentRoot = null;
		String currentParent = null;
				
		HSSFWorkbook workbook = null;
		try {
            workbook = new HSSFWorkbook(new POIFSFileSystem(in));
            
            HSSFSheet sheet = workbook.getSheetAt(0);

            //获取多少行
            int rows = sheet.getPhysicalNumberOfRows();
            
            if(rows < 1) {
            	throw new LogicException("此excel没有分类数据, 请检查");
            }
            
            //遍历每一行，注意：第 0 行为标题
            for (int rowNum = 0; rowNum < rows; rowNum++) {

            	String cat1Data = this.getCellValue(sheet, rowNum, 0);		// 1级分类数据, 可能是空
            	String cat2Data = this.getCellValue(sheet, rowNum, 1);		// 2级分类数据, 可能是空
            	String cat3Data = this.getCellValue(sheet, rowNum, 2);		// 3级分类数据, 不可能空

            	if(CheckUtils.isNotEmpty(cat1Data)) {
            		currentRoot = cat1Data;
            	}

            	if(CheckUtils.isNotEmpty(cat2Data)) {
            		currentParent = cat2Data;
            	}
        		
        		// 对于1级数据, 名字只要相同就是重复数据
        		// 对于2级数据, 还要看父分类是否相同
        		// 对于3级数据, 要看父分类和根分类是否相同
            	this.checkEach(errors, 1, nameMap, cat1Data, rowNum, null, null);
            	this.checkEach(errors, 2, nameMap, cat2Data, rowNum, currentRoot, null);
            	this.checkEach(errors, 3, nameMap, cat3Data, rowNum, currentRoot, currentParent);            	
            	
            }

        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } finally {
//        	try {
//				
//			} catch (IOException e) {
//	            logger.error(e.getMessage(),e);
//			}
        }
		
		return checkResult;
	}
	
	// 检查每一个cell的值. 
	private void checkEach(List<String> errors, int level, Map<String, TempData> map, String name, 
			int rowIndex, String currentRoot, String currentParent) {

		if(CheckUtils.isEmpty(name))return;
		
		String key = name;
		if(level == ProductCategory.LEVEL_PARENT) {
			key = currentParent + "_" + name;
		}else if(level == ProductCategory.LEVEL_CHILD) {
			key = currentRoot + "_" + currentParent + "_" + name;			
		}
		
		if(map.isEmpty() || map.get(key) == null) {
			map.put(key, new TempData(rowIndex, level, name));
			return;
		}
		
		// 如果来到这, 就是数据重复了. 		
		int existIndex = Integer.parseInt(map.get(key).toString());
		String msg = level + "级分类数据重复: 第" + (rowIndex + 1) + "行和第" + (existIndex + 1) + "行重复. ";
		errors.add(msg);		
	}
	
	// 获取db的数据, 同样形成值的map
	private Map<String, String> getAllDbData(){

		List<Map> allCates = commonDao.getAll(this.tableName);
		
		// 用3次循环划分好 1, 2, 3 级分类
		Map<Long, String> rootMap = new HashMap<>(); 
		Map<Long, String> parentMap = new HashMap<>(); 

		// 最终返回的数据
		Map<String, String> dbNameMap = new HashMap<>();

		for(Map data : allCates) {
			String name = data.get("name").toString();
			int level = Integer.parseInt(data.get("level").toString());

			Long id = Long.valueOf(data.get("id").toString());
			
			if(level == ProductCategory.LEVEL_ROOT) {
				dbNameMap.put(name, name);	
				rootMap.put(id, name);
			}
			else if (level == ProductCategory.LEVEL_PARENT) {
				parentMap.put(id, name);
			}
			
		}
		
		// 检查2级分类数据
		Map<Long, Long> parentRoot = new HashMap<>();	// 父和根的关联
		for(Map data : allCates) {
			String name = data.get("name").toString();
			int level = Integer.parseInt(data.get("level").toString());
			// 2, 3 级分类parent不可能是空
			Long parentId = Long.valueOf(data.get("parentId").toString());
			Long id = Long.valueOf(data.get("id").toString());
			
			if (level == ProductCategory.LEVEL_PARENT) {
				String rootName = rootMap.get(parentId).toString();
				String key = rootName + "_" + name;
				dbNameMap.put(key, name);
				
				parentRoot.put(id, parentId);
			}			
						
		}	
		// 3 级分类要连取两数据, 比较复杂
		for(Map data : allCates) {
			String name = data.get("name").toString();
			int level = Integer.parseInt(data.get("level").toString());
			// 2, 3 级分类parent不可能是空
			Long parentId = Long.valueOf(data.get("parentId").toString());
			
			if (level == ProductCategory.LEVEL_CHILD) {
				Long rootId = parentRoot.get(parentId);
				String rootName = rootMap.get(rootId).toString();
				String parentName = parentMap.get(parentId).toString();
				String key = rootName + "_" + parentName + "_" + name;
				dbNameMap.put(key, name);
			}			
						
		}	
		
		return dbNameMap;
	}
	
	private class CheckExcelResult {
		List<String> errors; 
		Map<String, TempData> nameMap;		
	}
	
	private class TempData {
		public int rowIndex;
		public int level;
		public String name;
		public TempData(int rowIndex, int level, String name) {
			super();
			this.rowIndex = rowIndex;
			this.level = level;
			this.name = name;
		}
	}
}
