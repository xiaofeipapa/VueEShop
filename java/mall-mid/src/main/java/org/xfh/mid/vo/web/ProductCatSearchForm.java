package org.xfh.mid.vo.web;

import org.xfh.dcore.vo.IndexSearchForm;

public class ProductCatSearchForm extends IndexSearchForm{
	String q;		// 查询关键字
	Integer cat;		// 根据分类搜索

	public Integer getCat() {
		return cat;
	}

	public void setCat(Integer cat) {
		this.cat = cat;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}	
	
}

