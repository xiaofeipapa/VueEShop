package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.ModalSpecGroup;
import org.xfh.mid.db.po.ModalSpecValue;

/**
 * 带属性关联子表
 * @author cys
 *
 */
public class ModalSpecGroupVo extends ModalSpecGroup {
	
	List<ModalSpecValue> children = new ArrayList<>();
	
	public ModalSpecGroupVo() {}

	public ModalSpecGroupVo(Long id, String name) {
		this.id = id;
		this.name = name;
	}	


	public List<ModalSpecValue> getChildren() {
		return children;
	}

	public void setChildren(List<ModalSpecValue> children) {
		this.children = children;
	}

	public void addChildren(ModalSpecValue child) {
		this.children.add(child);
	} 
	
	
}
