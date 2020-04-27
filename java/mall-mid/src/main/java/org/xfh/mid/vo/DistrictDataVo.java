package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.DistrictData;
/**
 * 省份/市/区的数据
 * @author Administrator
 * @date 2018年5月4日上午11:58:06
 */
public class DistrictDataVo extends DistrictData{

	List<DistrictDataVo> children = new ArrayList<>();   //下 级    3级没有下级

	String parentName;	
	String rootName;
	String rootCode;
	public List<DistrictDataVo> getChildren() {
		return children;
	}
	public void setChildren(List<DistrictDataVo> children) {
		this.children = children;
	}
	public void addChild(DistrictDataVo child) {
		this.children.add(child);
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
	public String getRootCode() {
		return rootCode;
	}
	public void setRootCode(String rootCode) {
		this.rootCode = rootCode;
	}
}
