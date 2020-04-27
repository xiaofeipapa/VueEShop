package org.xfh.mid.db.po;

import java.io.Serializable;
import java.util.Comparator;

import org.xfh.dcore.pojo.BaseTreeData;

/**
 * 后台树形菜单
 *
 */
public class BackMenu extends BaseTreeData implements Serializable, Comparator<BackMenu>{

	private static final long serialVersionUID = -5930890395703450867L;

	// 分类层级, 如1,2级
	public static final int MENU_PARENT = 1;
	public static final int MENU_CHILD = 2;
	
	protected String link;					// 链接
	protected String hidden;					// 二级菜单时, 该页面是否隐藏.  Y 或者 N 
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	@Override
	public int compare(BackMenu o1, BackMenu o2) {
		return o1.getOrder() - o2.getOrder();
	}
}
