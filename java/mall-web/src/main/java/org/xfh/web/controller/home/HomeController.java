package org.xfh.web.controller.home;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.mid.db.po.BottomLink;
import org.xfh.mid.vo.HelpArticleVo;
import org.xfh.mid.vo.HomeFloorVo;
import org.xfh.web.controller.BaseMallFrontController;
import org.xfh.web.service.IHomeService;
import org.xfh.web.vo.WebTopNav;

@RestController
public class HomeController extends BaseMallFrontController {
	
    static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    IHomeService homeService;


    // 获取首页底部的5个分类和子文章
	@RequestMapping("/homeIndex/getBottomArticles")
	public void getBottomArticles() {

		List<HelpArticleVo> list = homeService.getAllHelpArticle();

		DWebUtils.ajaxSucc(list);
		
	}

    // 获取首页底部的链接
	@RequestMapping("/homeIndex/getBottomLinks")
	public void getBottomLinks() {

		List<BottomLink> list = homeService.getAllBottomLink();

		DWebUtils.ajaxSucc(list);
		
	}

    // 获取楼层商品数据
	@RequestMapping("/homeIndex/getAllHomeFloor")
	public void getAllHomeFloor() {

		List<HomeFloorVo> volist = homeService.getAllHomeFloor();

		DWebUtils.ajaxSucc(volist);
		
	}

    // 获取搜索关键字和面包屑广告
	@RequestMapping("/homeIndex/getNavData")
	public void getNavData() {

		WebTopNav data = homeService.getTopNavData();

		DWebUtils.ajaxSucc(data);
		
	}
	
	
	
}
