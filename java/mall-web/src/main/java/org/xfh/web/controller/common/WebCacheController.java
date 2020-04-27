package org.xfh.web.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.web.controller.BaseMallFrontController;

/**
 * 封装可以被前端缓存的数据. 
 * 
 * @author cys
 *
 */
@RestController
public class WebCacheController extends BaseMallFrontController{

    static final Logger logger = LoggerFactory.getLogger(WebCacheController.class);


//	@RequestMapping("/common/getProductPageData")
//	public void getProductPageData(IndexSearchForm form) throws Exception {
//		
//		PageHolder<ProductVo> page = productService.getProductPage(form);
//
//		DWebUtils.ajaxSucc(page);
//	}
	
}
