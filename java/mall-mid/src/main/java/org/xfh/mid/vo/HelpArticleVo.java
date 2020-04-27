package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

import org.xfh.mid.db.po.HelpArticle;

/**
 * @author cys
 *
 */
public class HelpArticleVo extends HelpArticle{
	
	List<HelpArticleVo> children = new ArrayList<>();

	public List<HelpArticleVo> getChildren() {
		return children;
	}

	public void setChildren(List<HelpArticleVo> children) {
		this.children = children;
	}	
	
}
