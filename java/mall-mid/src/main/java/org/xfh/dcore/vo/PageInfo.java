package org.xfh.dcore.vo;

/**
 * 保存分页等信息。dao根据这个参数以确定分页信息。
 * 
 * @author 陈云师
 *
 */
public class PageInfo {
	private int pageSize = 10;
	private int pageNo = 1;			// 页码, 如第1页的1

	public PageInfo(){}

	public PageInfo(int pageNo, int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageInfo setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public int getPageNo() {
		return pageNo;
	}

	public PageInfo setPageNo(int pageNo) {
		this.pageNo = pageNo;
		return this;
	}

	public int getBeginRow() {
		if(pageNo < 1){
			return 0;
		}
		return (pageNo - 1) * pageSize;
	}
	
}
