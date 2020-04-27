package org.xfh.web.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IDistrictDataService;
import org.xfh.mid.biz.service.IHelpArticleService;
import org.xfh.mid.vo.ProductVo;
import org.xfh.web.controller.BaseMallFrontController;
import org.xfh.web.service.IWebProductService;

/**
 * 封装各模块使用的通用数据. 
 * @author cys
 *
 */
@RestController
public class WebCommonDataController extends BaseMallFrontController{

    static final Logger logger = LoggerFactory.getLogger(WebCommonDataController.class);

    @Autowired
    IWebProductService productService;

    @Autowired
    IDistrictDataService districtService;

    @Autowired
    IHelpArticleService helpService;

	@RequestMapping("/common/getProvinceList")
	public void getProvinceList() throws Exception {
		
		DWebUtils.ajaxSucc(districtService.getAllDistrictData());
	}

	@RequestMapping("/common/getHelpList")
	public void getHelpList() throws Exception {
		
		DWebUtils.ajaxSucc(helpService.getParentWithChildren(null));
	}
	
}
