package org.xfh.dcore.service.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;

/**
 * 
 * 这个基类的目的是减少大量重复的增删改查表的操作, 让mapper.xml真正保存有用的业务sql 
 * 
 * 1) 对于那些单表增删改查的业务模块, 继承这个类之后基本上不需要写新建/更新/单表查询的service代码了. 
 * 2) 默认实现了一个无关联表的分页查询. 如果有关联的分页, 还是要自己实现.
 * 
 * @author cys
 *
 */
public abstract class AbstractSingleTableService<T> implements ISingleTableService<T>, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(AbstractSingleTableService.class);

	protected String tableName = null;
	protected Class<T> clazz = null;
	protected List<String> defaultUpdateAvoidFields;	// 默认在新建/修改时忽略的字段(如外键字段)
	
	@Autowired
	protected CommonDao commonDao;
	
	@Autowired
	protected CommonDaoHelper daoHelper;

	@Override
	public void afterPropertiesSet() throws Exception {

		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

//		System.out.print("=============== simple name: " + clazz.getSimpleName());
//		System.out.print("=============== con name: " + clazz.getCanonicalName());
		this.tableName = clazz.getSimpleName();
		
		this.defaultUpdateAvoidFields = new ArrayList<>();
		Map<String,Method> fieldMap = RefUtils.findFieldWithGetter(clazz.newInstance());
		for(String name : fieldMap.keySet()) {
			if(name.startsWith("ex") || name.startsWith("EX")) {
				logger.debug("== ex field: " + name);
				this.defaultUpdateAvoidFields.add(name);
			}
		}
	}

	// 在保存之前进行业务字段的检查
	abstract protected void checkBeforeCreateOrUpdate(T entity, boolean isCreate) throws LogicException;
		
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Long saveWithCheck(Long userId, T entity, String[] avoidFieldsForUpdate) throws Exception {
		
		// 自动将ex字段排除
		String[] avoidFields = RefUtils.checkAndMakeProperAvoidFields(this.defaultUpdateAvoidFields, avoidFieldsForUpdate);

		Long id = RefUtils.getLong(entity, "id");
		boolean isCreate = (id == null || id.intValue() <= 0);

		this.checkBeforeCreateOrUpdate(entity, isCreate);

		if (isCreate) {
			RefUtils.setPropertyNull(entity, "id");
			this.setCreateInfoIfExist(userId, entity);
			id = this.insertNoCheck(userId, entity, null);
		} else {
			// 如果有 updateUserId 和 updateTime 字段, 设置之. 有时候会没有.
			this.setUpdateInfoIfExist(userId, entity);
			this.updateById(entity, avoidFields);
		}
		return id;
	}

	private void setCreateInfoIfExist(Long userId, T entity) {
		try {
			Map<String, Method> dmap = RefUtils.findFieldWithSetter(entity);
			if (userId !=null && dmap.containsKey(CREATE_USER_ID)) {
				Method m = dmap.get(CREATE_USER_ID);
				m.invoke(entity, userId);
			}
			if (dmap.containsKey(CREATE_TIME)) {
				Method m = dmap.get(CREATE_TIME);
				m.invoke(entity, new Date());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	private void setUpdateInfoIfExist(Long userId, T entity) {
		try {
			Map<String, Method> dmap = RefUtils.findFieldWithSetter(entity);
			if (dmap.containsKey(UPDATE_USER_ID)) {
				Method m = dmap.get(UPDATE_USER_ID);
				m.invoke(entity, userId);
			}
			if (dmap.containsKey(UPDATE_TIME)) {
				Method m = dmap.get(UPDATE_TIME);
				m.invoke(entity, new Date());
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	@Transactional
	public Long insertNoCheck(Long userId,T entity, String[] inputAvoidFields) throws Exception {
		// 自动将ex字段排除
		String[] avoidFields = RefUtils.checkAndMakeProperAvoidFields(this.defaultUpdateAvoidFields, inputAvoidFields);
		this.setCreateInfoIfExist(userId, entity);
		TreeMap<String, Object> dataMap = this.copyPojoToMap(entity, avoidFields);
		commonDao.insertOne(this.tableName, dataMap);
		if(dataMap.get("id") == null) return null;
		Long id = Long.parseLong(dataMap.get("id").toString());
		return id;
	}

	protected TreeMap<String, Object> copyPojoToMap(T entity, String[] avoidFields) throws Exception {
//		String[] avoidFields = null;
//		Method method = RefUtils.getMethodByName(entity, "avoidUpdateFields");
//		if(method != null ) {
//			avoidFields = (String[]) method.invoke(entity, null);
//		}
		
		return RefUtils.copyFieldsToMap(entity, false, avoidFields, null);
	}

	protected TreeMap<String, Object> copyPojoToMap2(Object entity, String[] avoidFields) throws Exception {
//		String[] avoidFields = null;
//		Method method = RefUtils.getMethodByName(entity, "avoidUpdateFields");
//		if(method != null ) {
//			avoidFields = (String[]) method.invoke(entity, null);
//		}
		return RefUtils.copyFieldsToMap(entity, false, avoidFields, null);
	}

	@Override
	@Transactional
	public void updateById(T entity,String[] inputAvoidFields) throws Exception {
		// 自动将ex字段排除
		String[] avoidFields = RefUtils.checkAndMakeProperAvoidFields(this.defaultUpdateAvoidFields, inputAvoidFields);
		TreeMap<String, Object> dataMap = this.copyPojoToMap(entity, avoidFields);
		Long id = RefUtils.getLong(entity, "id");
		commonDao.updateById(this.tableName, dataMap, id);
	}

	@Override
	@Transactional
	public void updateByFilter(T entity, IndexSearchFilter filter, String[] inputAvoidFields) throws Exception {
		// 自动将ex字段排除
		String[] avoidFields = RefUtils.checkAndMakeProperAvoidFields(this.defaultUpdateAvoidFields, inputAvoidFields);
		TreeMap<String, Object> dataMap = this.copyPojoToMap(entity, avoidFields);
		Long id = RefUtils.getLong(entity, "id");
		commonDao.updateByFilter(this.tableName, dataMap, filter);

	}

	@Override
	@Transactional
	public void updateInIds(TreeMap<String, Object> dataMap, List<Long> idList) throws Exception {

		commonDao.updateInIds(this.tableName, dataMap, idList);

	}
	
	@Override
	public PageHolder<T> getPage(IndexSearchFilter searchFilter) throws Exception {
		
		List<Map> mapList = commonDao.getPageListByFilter(this.tableName, searchFilter);
		int totalCount = commonDao.getCountByFilter(this.tableName, searchFilter);
		
		List<T> dataList = new ArrayList<T>();
		if(mapList !=null){
			for(Map mdata : mapList){
				T target = (T)RefUtils.copyFromMapToObject(mdata, this.clazz, false, null);
				dataList.add(target);
			}
		}
		
		PageHolder<T> holder = new PageHolder<T>();
		holder.setPageSize(searchFilter.getPageSize());
		holder.setPageNo(searchFilter.getPageNo());
		holder.setDataList(dataList);
		holder.setTotalCount(totalCount);
		
		// 计算分页数量
		holder.setPageCount(this.calculatePageCount(totalCount, searchFilter.getPageSize()));
		
		return holder;
	}
		
	protected int calculatePageCount(int totalCount, int pageSize) {
		if(totalCount < pageSize) {
			return 1;
		}

	    int t = totalCount / pageSize;
	    if(totalCount % pageSize != 0) {
	    	t += 1;
	    }
	    return t;
	}

	@Override
	public int getCountByFilter(IndexSearchFilter searchFilter) {
		int totalCount = commonDao.getCountByFilter(this.tableName, searchFilter);
		return totalCount;
	}

	@Override
	public T getById(Long id) {
		Map data = commonDao.getById(this.tableName, id);
		if(data == null)return null;
		return (T) RefUtils.copyFromMapToObject(data, this.clazz, false, null);
	
	}

	@Override
	public List<T> getListInIds(List<Long> idList) {

        List dataList = new ArrayList();
        if(CheckUtils.isEmptyCollection(idList)){
			return dataList;
        }
        
        String sql = DSqlUtils.andInValues("id", idList);

        List<Map> mapList = commonDao.getListByFilter(this.tableName, null);
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}
	
	protected <T> T convertMybatisMapToObject(Map data){
		if(data == null)return null;
		return (T) RefUtils.copyFromMapToObject(data, this.clazz, false, null);
	}

	@Override
	public List<T> getListByFilter(IndexSearchFilter searchFilter) {
		List<Map> mapList = commonDao.getListByFilter(this.tableName, searchFilter);
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}

	@Override
	@Transactional
	public void deleteById(Long loginUserId,Long id)  throws Exception{
		commonDao.deleteById(this.tableName, id);
	}
	
	/**
	 * 如果只检查一个字段, moreSql是null. 否则传入sql条件
	 * @param field
	 * @param value
	 * @param msg
	 * @param id
	 * @param isCreate
	 * @param moreFilter
	 * @return
	 * @throws LogicException
	 */
	protected Long checkDuplicateByField(String field, Object value, String msg, Long id, boolean isCreate) throws LogicException {

		Long existId = null;
		Map existData = commonDao.getOneByField(this.tableName, field, value);
		if(existData != null) {
			// FIXME 这里有严重的问题, 不能假定都是id, 需要思考一个更好的做法. 
			existId = Long.valueOf(existData.get("id").toString());
		}

		if(existId != null && existId > 0){
			if(isCreate){
				throw new LogicException(msg);
			}
			else if( existId.longValue() != id.longValue()){
				throw new LogicException(msg);
			}
		}
		return existId;
	}

	protected Long checkDuplicateBySql(String sqlFilter, String msg, Long id, boolean isCreate) throws LogicException {

		T data = this.getOneBySql(sqlFilter);
		Long existId = null;
		
		if(data != null) {
			existId = RefUtils.getLong(data, "id");
		}

		if(existId != null && existId > 0){
			if(isCreate){
				throw new LogicException(msg);
			}
			else if( existId.longValue() != id.longValue()){
				throw new LogicException(msg);
			}
		}
		return existId;
	}

	@Override
	public T getOneBySql(String sqlFilter) {
		Map data = commonDao.getOneBySql(this.tableName, sqlFilter);
		if(data == null)return null;
		return (T) RefUtils.copyFromMapToObject(data, this.clazz, false, null);
	}

	@Override
	public T getOneByField(String fieldName, Object fieldValue) {
		Map data = commonDao.getOneByField(this.tableName, fieldName, fieldValue);
		if(data == null)return null;
		return (T) RefUtils.copyFromMapToObject(data, this.clazz, false, null);
	}

	@Override
	public void deleteBySql(Long loginUserId,String sql)  throws Exception{
		commonDao.deleteBySql(this.tableName, sql);
	}

	@Override
	public int getCountBySql(String filterSql) {
		return commonDao.getCountBySql(this.tableName, filterSql);
	}

	@Override
	public List<T> getAll(){
		List<Map> mapList = commonDao.getAll(this.tableName);
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}

	@Override
	public List<T> getListBySql(String sql) {
		List<Map> mapList = commonDao.getListBySql(this.tableName, sql);
		List dataList = new ArrayList();
		for(Map mmm : mapList){
			Object o = this.convertMybatisMapToObject(mmm);
			if(o != null){
				dataList.add(o);				
			}
		}
		return dataList;
	}	


	@Override
	public void deleteForOrderCase(Long loginUserId, Long id, String orderField)  throws Exception{
		// 删除的时候, 要确保order属性
		
		Map theData = commonDao.getById(this.tableName, id);
		if(theData == null) {
			return;
		}
		
		Integer theOrder = Integer.valueOf(theData.get(orderField).toString());
		
		// 比这个order小的数据不用动, 比它大的数据均需要 order -1
		String sqlFilter = DSqlUtils.andGtNumber(orderField, theOrder, true);
		
		commonDao.deleteById(this.tableName, id);
		commonDao.subtractFieldBySql(this.tableName, orderField, 1, sqlFilter);
		
		this.deleteById(loginUserId, id);
	}

	@Override
	public void trashDataById(Long loginUserId, Long pk, String field, String value) {
		
		TreeMap<String, Object> param = new TreeMap<>();
		param.put(field, value);
		commonDao.updateById(this.tableName, param, pk);
	}
	
}
