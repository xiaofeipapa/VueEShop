package org.xfh.dcore.dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;

/**
 * 为减轻重复代码而设的dao . 注意,主键名字都必须是id, 且为Long 或 int 类型. 
 * 需要配合 MybatisHelper使用
 * @author cys
 */

public interface CommonDao {
	
	// 创建单表数据
	Long insertOne(@Param("tableName")String tableName, @Param("dataMap")TreeMap<String, Object> dataMap);
	
	// 根据id更新单表数据
	Long updateById(@Param("tableName")String tableName,
			@Param("dataMap")TreeMap<String, Object> dataParams, @Param("id")Long id);

	// 根据filter更新单表数据
	/**
	 * @deprecated 旧代码, 定时删除
	 * @param tableName
	 * @param dataParams
	 * @param filter
	 * @return
	 */
	Long updateByFilter(@Param("tableName")String tableName,
							 @Param("dataMap")TreeMap<String, Object> dataParams, @Param("searchFilter")IndexSearchFilter filter);

	// 更新符合id的记录
	Long updateInIds(@Param("tableName")String tableName,
							 @Param("dataMap")TreeMap<String, Object> dataParams, @Param("idList")List<Long> idList);

	// 更新符合sql查询语句的记录
	Long updateBySql(@Param("tableName")String tableName,
							 @Param("dataMap")TreeMap<String, Object> dataParams, @Param("sqlFilter")String sqlFilter);
	
	// 根据参数更新单表数据
	Long updateByParams(@Param("tableName")String tableName,
			@Param("dataMap")TreeMap<String, Object> dataMap, @Param("filterParams")TreeMap<String, Object> filterParams);
		
	// 删除单表数据
	Long deleteById(@Param("tableName")String tableName, @Param("id")Long id);
	
	// 根据条件(sqlFilter 删除) 
	Long deleteBySql(@Param("tableName")String tableName, @Param("sqlFilter")String sqlFilter);
		
	// 根据字段值返回第一个数据的id, 用于简单表的创建/更新重复检查. 
	// Long getOneById(@Param("tableName")String tableName, @Param("fieldName")String fieldName, @Param("fieldValue")Object fieldValue);

	int getCountByFilter(@Param("tableName")String tableName,@Param("searchFilter")IndexSearchFilter searchFilter);
	int getCountBySql(@Param("tableName")String tableName,@Param("filterSql")String filterSql);
	List<Map> getListByFilter(@Param("tableName")String tableName,@Param("searchFilter")IndexSearchFilter searchFilter);
	List<Map> getListBySql(@Param("tableName")String tableName,@Param("filterSql")String filterSql);
	List<Map> getPageListByFilter(@Param("tableName")String tableName,@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 获取某个字段的最大值. 通常用于设置排序值的情况
	int getMaxBySql(@Param("tableName")String tableName, @Param("fieldName")String fieldName, @Param("filterSql")String filterSql);

	Map getById(@Param("tableName")String tableName, @Param("id")Long id);	
	Map getOneByField(@Param("tableName")String tableName, @Param("fieldName")String fieldName, @Param("fieldValue")Object fieldValue);
	Map getOneBySql(@Param("tableName")String tableName, @Param("sqlFilter")String sqlFilter);

	List<Map> getAll(@Param("tableName")String tableName);
	
	// 根据自定义主键更新数据
	Long updateByCustomPk(@Param("tableName")String tableName,
			@Param("dataMap")TreeMap<String, Object> dataMap, @Param("pkName")String pkName);
	
	// 根据条件, 将排序字段的值 - theCount
	Long subtractFieldBySql(@Param("tableName")String tableName, @Param("fieldName")String fieldName, 
			@Param("theCount")Integer theCount, @Param("sqlFilter")String sqlFilter);
	
	// 根据条件, 将排序字段的值 + theCount
	Long addFieldBySql(@Param("tableName")String tableName, @Param("fieldName")String fieldName, 
			@Param("theCount")Integer theCount, @Param("sqlFilter")String sqlFilter);
	
	// 相加某个字段
	Integer sumFieldBySql(@Param("tableName")String tableName, @Param("fieldName")String fieldName,
			@Param("sqlFilter")String sqlFilter);
}
