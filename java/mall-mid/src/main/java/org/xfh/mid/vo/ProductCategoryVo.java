package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.ProductCategory;
/**
	商品分类结构
 */
public class ProductCategoryVo extends ProductCategory{

	private static final long serialVersionUID = 3654422270439276804L;

	Integer tempId;

	List<ProductCategoryVo> children = new ArrayList<>();   //下 级    3级没有下级

	public Integer getTempId() {
		return tempId;
	}

	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}

	public List<ProductCategoryVo> getChildren() {
		return children;
	}

	public void setChildren(List<ProductCategoryVo> children) {
		this.children = children;
	}

	public void addChild(ProductCategoryVo dto) {
		this.children.add(dto);
	}
	
	
}
