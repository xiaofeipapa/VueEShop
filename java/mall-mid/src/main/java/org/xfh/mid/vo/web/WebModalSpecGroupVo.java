package org.xfh.mid.vo.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品模型的规格属性组, 规格属性列表
 * @author cys
 *
 */
public class WebModalSpecGroupVo {
	
	Long id;
	String name;
	
	List<WebModalSpecValueVo> children = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WebModalSpecValueVo> getChildren() {
		return children;
	}

	public void setChildren(List<WebModalSpecValueVo> children) {
		this.children = children;
	}
}
