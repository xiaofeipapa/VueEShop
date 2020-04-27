package org.xfh.dcore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageHolder<T> implements Serializable {

	private static final long serialVersionUID = 4547642217911831397L;
			
	private int pageNo;					//如第1页，第2页。。。
	private int pageSize;						//每页有多少条记录
	private long pageCount;						//多少页
	private long totalCount;					//总记录数
	private List<T> dataList = new ArrayList<T>();    //分页的数据列表
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
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
}
