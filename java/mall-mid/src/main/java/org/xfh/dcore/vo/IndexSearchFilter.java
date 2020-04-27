package org.xfh.dcore.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DStringUtils;

/**
 * 列表页查询工具类. 用这个类能方便列表页带 like 查询的场景. 
 * 
 * 见 Vue的 RoleIndex.vue 
 * 
 * @author cys
 *
 */
public class IndexSearchFilter {
	protected String likeValue;		// 需要查询的like 内容
	protected String[] likeKeys;		// like对应的数据库字段. 例如查询name like '最新产品' 的记录. 这时候 likeKeys 就是name, textLike 就是 '%最新产品%'

	protected List<MyOrderBy> orderByList = new ArrayList<>(); // 排序
	protected String orderBy;
	protected int pageNo;
	protected int pageSize;
	protected int offset;
	
	protected String moreSql;		// 如果是like 查询尽量用like key
	
	public static IndexSearchFilter fromIndexForm(IndexSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText()
				, new PageInfo(form.getPageNo(), form.getPageSize()));
		
		filter.setOrderByList(form.getOrderByList());
		
		return filter;
	}

//	public IndexSearchFilter(String searchText, PageInfo pageInfo, MyOrderBy...orderBy){
//		
//		this.initInternal(searchText, pageInfo);
//		for(MyOrderBy ob : orderBy) {
//			this.orderByList.add(ob);
//		}
//	}

	public IndexSearchFilter(String searchText, PageInfo pageInfo){
		
		this.initInternal(searchText, pageInfo);
	}
	
	private void initInternal(String searchText, PageInfo pageInfo) {

		this.makeLikeForm(searchText);
		this.pageSize = pageInfo.getPageSize();
		this.pageNo = pageInfo.getPageNo();
		this.offset = pageInfo.getBeginRow();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	

	public IndexSearchFilter(){
				
	}

	private void makeLikeForm(String searchText){

		if( ! CheckUtils.isEmpty(searchText)){
			searchText = DStringUtils.removeSpecial(searchText.trim());
			searchText = "%" + searchText + "%";
		}else{
			searchText = null;
		}
		this.likeValue = searchText;	
	}

	public String getLikeValue() {
		return likeValue;
	}

	public String[] getLikeKeys() {
		return likeKeys;
	}

	public void setLikeKeys(String[] likeKeys) {
		this.likeKeys = likeKeys;
	}

	public String getMoreSql() {
		return moreSql;
	}

	public void setMoreSql(String moreSql) {
		this.moreSql = moreSql;
	}

	public List<MyOrderBy> getOrderByList() {
		return orderByList;
	}
	public void setOrderByList(List<MyOrderBy> orderByList) {
		this.orderByList = orderByList;
	}
	public void addOrderBy(MyOrderBy orderBy) {
		this.orderByList.add(orderBy);
	}
	public void addOrderBy(String name, String orderBy) {
		this.orderByList.add(new MyOrderBy(name, orderBy));
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
