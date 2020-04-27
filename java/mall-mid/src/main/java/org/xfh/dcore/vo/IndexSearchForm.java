package org.xfh.dcore.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用于列表查询. 
 * 
 * @author cys
 *
 */
public class IndexSearchForm {

	protected String searchText;
	protected String searchStatus;
	protected List<MyOrderBy> orderByList = new ArrayList<>(); // 排序
	protected int pageSize;
	protected int pageNo;
	protected String startDate;			// 开始日期
	protected String endDate;			// 结束日期
	
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
