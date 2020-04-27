package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.BackMenu;

// -- 左侧树形菜单
public class BackMenuVo extends BackMenu{

	private static final long serialVersionUID = 1056587847247568961L;
	
	List<BackMenuVo> children = new ArrayList<BackMenuVo>();

	public List<BackMenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<BackMenuVo> children) {
		this.children = children;
	}

	public void addChild(BackMenuVo child) {
		this.children.add(child);
	}
}