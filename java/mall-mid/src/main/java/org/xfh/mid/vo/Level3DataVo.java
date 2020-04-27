package org.xfh.mid.vo;

import java.util.List;

/**
 * 孙子分类vo, 用于选择分类时. 
 * @author cys
 *
 */
public class Level3DataVo{
	
	Long id;
	Long parentId;
	Long rootId;
	
	String name;	
	String parentName;	
	String rootName;
	String code;
	
	List<Level3DataVo> childOptions;
	List<Level3DataVo> parentOptions;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getRootId() {
		return rootId;
	}
	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getRootName() {
		return rootName;
	}
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<Level3DataVo> getChildOptions() {
		return childOptions;
	}
	public void setChildOptions(List<Level3DataVo> childOptions) {
		this.childOptions = childOptions;
	}
	public List<Level3DataVo> getParentOptions() {
		return parentOptions;
	}
	public void setParentOptions(List<Level3DataVo> parentOptions) {
		this.parentOptions = parentOptions;
	}
}
