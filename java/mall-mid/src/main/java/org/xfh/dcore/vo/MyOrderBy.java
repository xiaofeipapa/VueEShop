package org.xfh.dcore.vo;

public class MyOrderBy {
	String name;
	String orderBy;		// desc or asc
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public MyOrderBy(String name, String orderBy) {
		super();
		this.name = name;
		this.orderBy = orderBy;
	}
	public MyOrderBy() {
		super();
	}
	
}
