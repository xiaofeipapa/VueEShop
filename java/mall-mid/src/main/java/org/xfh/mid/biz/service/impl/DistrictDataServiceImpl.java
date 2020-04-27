package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.biz.service.IDistrictDataService;
import org.xfh.mid.db.dao.DistrictDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.DistrictData;
import org.xfh.mid.vo.DistrictDataVo;
import org.xfh.mid.vo.DistrictWithParentVo;

@Service
public class DistrictDataServiceImpl extends AbstractSingleTableService<DistrictData> implements IDistrictDataService{

	private Logger logger = LoggerFactory.getLogger(DistrictDataServiceImpl.class);
	
	@Autowired
	DistrictDao districtDao;

	@Override
	protected void checkBeforeCreateOrUpdate(DistrictData entity, boolean isCreate) throws LogicException {


//		VUtils.checkMandatory(entity.getName(), "请输入分类名称");
//		if(entity.getLevel() != ProductCategory.LEVEL_ROOT) {
//			VUtils.checkMandatory(entity.getParentId(), "请选择父分类");			
//		}
//		
//		String error = "同一层级的分类名字已经存在: " + entity.getName();
//		
//		// 同一层级的分类名字不能重复
//		String sqlFilter = DSqlUtils.andEqInteger("level", entity.getLevel());
//		sqlFilter += DSqlUtils.andEqString("name", entity.getName());
//		sqlFilter += DSqlUtils.andEqLong("parentId", entity.getParentId());
//				
//		if(isCreate) {
//			// 新增的情况只需要检查重名
//			int existCount = commonDao.getCountBySql(this.tableName, sqlFilter);
//			if(existCount > 0) {
//				throw new LogicException(error);
//			}
//			
//			// 设置排序
//			String orderSql = DSqlUtils.andEqInteger("level", entity.getLevel());
//			orderSql += DSqlUtils.andEqLong("parentId", entity.getParentId());
//						
//			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", orderSql);
//			entity.setOrder(maxOrder + 1);
//			
//			// 设置默认数据
//			entity.setShowFront(ProductCategory.SHOW_FRONT_NO);
//			
//			return;
//		}
//
//		// 更新的情况, 如果记录数大于1肯定重复, 如果只是1, 检查是否本记录. 
//		
//		List<Map> existList = commonDao.getListBySql(this.tableName, sqlFilter);
//		if(CheckUtils.isEmptyCollection(existList)) {
//			return;
//		}
//		
//		if(existList.size() > 1) {
//			throw new LogicException(error);				
//		}
//		
//		Long existId = RefUtils.getLong(existList.get(0), "id");
//		if(existId.intValue() != entity.getId().intValue()) {
//			throw new LogicException(error);	
//			
//		}
	}

	@Override
	public Collection<DistrictDataVo> getAllDistrictData() {
		
		List<DistrictData> allData = this.getAll();
		
		Map<String, DistrictDataVo> provinceMap = new HashMap<>();
		Map<String, DistrictDataVo> cityMap = new HashMap<>();
		List<DistrictDataVo> dlist = new ArrayList<>();
		
		// 先找出省份
		for(DistrictData data : allData) {

			DistrictDataVo vo = new DistrictDataVo();
			RefUtils.copyFieldsToObject(data, vo);

			boolean isProvince = data.getLevel() == DistrictData.PROVINCE;
			boolean isCity = data.getLevel() == DistrictData.CITY;
			boolean isDistrict = data.getLevel() == DistrictData.DISTRICT;
			
			if(isProvince) {
				provinceMap.put(vo.getCode(), vo);
			}
			
			if(isCity) {
				cityMap.put(data.getCode(), vo);
			}
			
			if(isDistrict) {
				dlist.add(vo);
			}
		}

		// 找出城市并添加到省份
		
		for(DistrictDataVo province : provinceMap.values()) {

			for(DistrictDataVo vo : cityMap.values()) {
	
				String parentCode = vo.getParentCode();
				
				if(province.getCode().equalsIgnoreCase(parentCode)) {
					province.addChild(vo);;
				}
				
			}
			
		}


		// 找出区域信息并添加到城市
		for(DistrictDataVo city : cityMap.values()) {

			for(DistrictDataVo vo : dlist) {
	
				String parentCode = vo.getParentCode();
				
				if(city.getCode().equalsIgnoreCase(parentCode)) {
					city.addChild(vo);;
				}
				
			}
			
		}
		
		return provinceMap.values();

	}

	@Override
	public List<DistrictDataVo> getDistrictPageListForSearch(IndexSearchFilter searchFilter) {
		return districtDao.getDistrictPageListForSearch(searchFilter);
	}

	@Override
	public DistrictDataVo getDistrictVoByCode(String code) {
		return districtDao.getDistrictVoByCode(code);
	}

	@Override
	public DistrictWithParentVo getAllParentInfoByChildCode(String districtCode) throws Exception {
		
		// 找出 
		DistrictDataVo districtVo = districtDao.getDistrictVoByCode(districtCode);
		DistrictDataVo cityVo  = districtDao.getDistrictVoByCode(districtVo.getParentCode());
		DistrictDataVo provinceVo  = districtDao.getDistrictVoByCode(cityVo.getParentCode());
		
		// 找出同一城市的区域列表
		String sql = DSqlUtils.andEqString(DBs.PARENT_CODE, districtVo.getParentCode(), false);
		List<DistrictData> districtOptions = daoHelper.getListBySql(DistrictData.class, sql);
		
		// 找出当前省份下的城市列表
		sql = DSqlUtils.andEqString(DBs.PARENT_CODE, cityVo.getParentCode(), false);
		List<DistrictData> cityOptions = daoHelper.getListBySql(DistrictData.class, sql);
		
		DistrictWithParentVo parentVo = new DistrictWithParentVo();
		parentVo.setDistrictCode(districtCode);
		parentVo.setDistrictName(districtVo.getName());
		parentVo.setCityCode(cityVo.getCode());
		parentVo.setCityName(cityVo.getName());
		parentVo.setProvinceCode(cityVo.getParentCode());
		parentVo.setProvinceName(provinceVo.getName());
		parentVo.setCityOptions(cityOptions);
		parentVo.setDistrictOptions(districtOptions);
		
		return parentVo;
	}


}


