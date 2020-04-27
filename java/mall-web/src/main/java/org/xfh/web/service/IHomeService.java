package org.xfh.web.service;

import java.util.List;

import org.xfh.mid.db.po.BottomLink;
import org.xfh.mid.vo.HelpArticleVo;
import org.xfh.mid.vo.HomeFloorVo;
import org.xfh.web.vo.WebTopNav;

/**
 * 前台首页service
 * @author cys
 *
 */
public interface IHomeService {
	
	List<HelpArticleVo> getAllHelpArticle();
	
	List<BottomLink> getAllBottomLink();
	
	// 获取楼层和横幅广告
	List<HomeFloorVo> getAllHomeFloor();
	
	WebTopNav getTopNavData();

}
