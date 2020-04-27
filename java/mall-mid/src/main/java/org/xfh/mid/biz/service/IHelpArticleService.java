package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.HelpArticle;
import org.xfh.mid.vo.HelpArticleVo;

public interface IHelpArticleService extends ISingleTableOrderService<HelpArticle>{
	
	// 删除前检查
	void checkAndDelete(Long loginUserId, Long id) throws Exception;
	
	// 返回所有父级分类
	List<HelpArticleVo> getParentWithChildren(IndexSearchFilter searchFilter);
	
	// 返回数据
	HelpArticleVo getVoById(Long id);
	
	void toggleShow(Long userId, Long id);
}
