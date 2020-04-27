package org.xfh.dcore.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;

@Component
public class CommonDaoHelper {
	
	@Autowired
	CommonDao commonDao;

	public <T> T getOneBySql(Class<T> clazz, String sql) {
		Map data = commonDao.getOneBySql(clazz.getSimpleName(), sql);
		return this.convertMybatisMapToObject(clazz, data);
	}
	
	public <T> T getById(Class<T> clazz, Long id) {
		Map data = commonDao.getById(clazz.getSimpleName(), id);
		return this.convertMybatisMapToObject(clazz, data);
	}
	
	public <T> int getCountBySql(Class<T> clazz, String sql) {
		return commonDao.getCountBySql(clazz.getSimpleName(), sql);
	}
	
	public <T> void deleteById(Class<T> clazz, Long id) {
		commonDao.deleteById(clazz.getSimpleName(), id);
	}
	
	public <T> void deleteBySql(Class<T> clazz, String sql) {
		commonDao.deleteBySql(clazz.getSimpleName(), sql);
	}

	public <T> List<T> getListBySql(Class<T> clazz, String sql) {
		List<Map> mapList = commonDao.getListBySql(clazz.getSimpleName(), sql);
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(clazz, mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}

	public <T> List<T> getAll(Class<T> clazz) {
		List<Map> mapList = commonDao.getAll(clazz.getSimpleName());
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(clazz, mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}

	public <T> List<T> getListByFilter(Class<T> clazz, IndexSearchFilter filter) {
		List<Map> mapList = commonDao.getListByFilter(clazz.getSimpleName(), filter);
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(clazz, mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}
	
	public <T> T convertMybatisMapToObject(Class<T> clazz, Map data){
		if(data == null)return null;
		return (T) RefUtils.copyFromMapToObject(data, clazz, false, null);
	}
	
	public <T> Long insertOne(Class<T> clazz, Object data) {
		TreeMap<String,Object> dataMap = RefUtils.copyFieldsToMap(data, false, null, null);
		commonDao.insertOne(clazz.getSimpleName(), dataMap);
		if(dataMap.get("id") == null) return null;
		return Long.parseLong(dataMap.get("id").toString());		
		
	}
	
	public <T> void updateById(Class<T> clazz, T data) {
		
		TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(data, false, new String[] {"id"}, null);
		Long id = RefUtils.getLong(data, "id");
		commonDao.updateById(clazz.getSimpleName(), dataMap, id);	
		
	}
	public <T> void updateBySql(Class<T> clazz, T data, String sql) {
		
		TreeMap<String, Object> dataMap = RefUtils.copyFieldsToMap(data, false, new String[] {"id"}, null);

		commonDao.updateBySql(clazz.getSimpleName(), dataMap, sql);
		
	}
	
	public <T> void updateFieldById(Class<T> clazz, Long id, String name, Object data) {
		
		TreeMap<String, Object> dataMap = new TreeMap<>();
		dataMap.put(name, data);
		commonDao.updateById(clazz.getSimpleName(), dataMap, id);	
		
	}
	
	public <T> int sumFieldBySql(Class<T> clazz, String fieldName, String sql) {

		return commonDao.sumFieldBySql(clazz.getSimpleName(), fieldName, sql);
		
	}
	
	public <T> void addFieldBySql(Class<T> clazz, String fieldName, int theCount, String sql) {

		commonDao.addFieldBySql(clazz.getSimpleName(), fieldName, theCount, sql);
		
	}
	
	public <T> void subtractFieldBySql(Class<T> clazz, String fieldName, int theCount, String sql) {

		commonDao.subtractFieldBySql(clazz.getSimpleName(), fieldName, theCount, sql);
		
	}
	
	public <T> PageHolder<T> makePage(Class<T> clazz, int totalCount, List<T> dataList, IndexSearchFilter searchFilter){
		
		PageHolder<T> holder = new PageHolder<T>();
		holder.setPageSize(searchFilter.getPageSize());
		holder.setPageNo(searchFilter.getPageNo());
		holder.setDataList(dataList);
		holder.setTotalCount(totalCount);
		
		// 计算分页数量
		holder.setPageCount(calculatePageCount(totalCount, searchFilter.getPageSize()));
		
		return holder;
	}
	
	public int calculatePageCount(int totalCount, int pageSize) {
		if(totalCount < pageSize) {
			return 1;
		}
	
	    int t = totalCount / pageSize;
	    if(totalCount % pageSize != 0) {
	    	t += 1;
	    }
	    return t;
	}
}
