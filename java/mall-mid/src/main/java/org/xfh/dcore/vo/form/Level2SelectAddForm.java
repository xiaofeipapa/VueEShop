package org.xfh.dcore.vo.form;

/**
 * 级联数据新增表单
 * 
 * @author cys
 *
 */
public class Level2SelectAddForm {
	Long parentId;
	String value;
	Integer level;
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
