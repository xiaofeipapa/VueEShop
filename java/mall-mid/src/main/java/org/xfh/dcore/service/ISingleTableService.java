package org.xfh.dcore.service;

import java.util.List;
import java.util.TreeMap;

import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;

/**
 * 封装单表的增删改查操作. 注意: 由于列表很可能带外键字段, 所以暂时不支持.
 * 
 * @author cys
 *
 */
public interface ISingleTableService<T> {

	public static final String CREATE_TIME = "createTime";
	public static final String UPDATE_TIME = "updateTime";
	public static final String CREATE_USER_ID = "createUserId";
	public static final String UPDATE_USER_ID = "updateUserId";

	// ================= 增加的相关方法
	// 增加或更新方法. 会通过 entity.id 来判断是增加还是更新
	Long saveWithCheck(Long userId, T entity, String[] avoidFieldsForUpdate) throws Exception;

	// 直接增加, 不检查重复性.
	Long insertNoCheck(Long userId,T entity, String[] avoidFields) throws Exception;

	// ================= 更新的相关方法

	// 根据id进行更新
	void updateById(T entity, String[] avoidFields) throws Exception;

	// 根据条件进行更新
	void updateByFilter(T entity, IndexSearchFilter filter, String[] avoidFields) throws Exception;
	
	// 更新符合id的数据
	void updateInIds(TreeMap<String, Object> dataMap, List<Long> idList) throws Exception;

	// ================= 查询的相关方法

	// 获取分页
	PageHolder<T> getPage(IndexSearchFilter searchFilter) throws Exception;
	
	// 获取记录数
	int getCountByFilter(IndexSearchFilter searchFilter);

	int getCountBySql(String filterSql);
	
	// 根据主键id 获取对象
	T getById(Long id);
	
	// 根据字段 获取对象, 如果有多个返回第一个
	T getOneByField(String name, Object value);
	
	// 根据sql 获取对象, 如果有多个返回第一个
	T getOneBySql(String sqlFilter);
	
	// 注意id的数量要小于1000个. 
	List<T> getListInIds(List<Long> idList);
	
	// 获取符合条件的记录列表
	List<T> getListByFilter(IndexSearchFilter searchFilter);
	
	// 获取符合条件的记录列表
	List<T> getListBySql(String sql);
	
	// 要注意! 返回数据不能太大
	List<T> getAll();

	// ================= 删除的相关方法
	void deleteById(Long loginUserId, Long pk) throws Exception;
	void deleteBySql(Long loginUserId, String sql) throws Exception;
	// 处理带排序的情况
	void deleteForOrderCase(Long loginUserId, Long id, String orderField) throws Exception;
	
	// ========== 业务方法: 标记删除
	void trashDataById(Long loginUserId, Long pk, String field, String value);
}
