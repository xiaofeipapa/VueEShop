package org.xfh.web.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.web.ProductCatSearchForm;
import org.xfh.mid.vo.web.WebProductVo;
import org.xfh.web.controller.BaseMallFrontController;
import org.xfh.web.service.IWebProductService;
import org.xfh.web.vo.ModalSpecMapVo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class WebProductController extends BaseMallFrontController {
	
    static final Logger logger = LoggerFactory.getLogger(WebProductController.class);

    @Autowired
    IWebProductService productService;

    // 获取商品
	@RequestMapping("/product/detail")
	public void getDetail(Long pid) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(false);

		WebProductVo vo = productService.getWebProductDetail(userId, pid);
		

		DWebUtils.ajaxSucc(vo);
		
	}

    // 根据模型和属性获取商品数据
	@RequestMapping("/product/findPidBySpec")
	public void findPidBySpec(String jsonData) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(false);
		
		logger.debug("=== jsonData: " + jsonData);
		
		ModalSpecMapVo form = new Gson().fromJson(jsonData, new TypeToken<ModalSpecMapVo>() {}.getType());

		Long productId = productService.getProductIdBySpec(userId, form);
		
		DWebUtils.ajaxSucc(productId);
		
	}


	@RequestMapping("/product/index")
	public void index(ProductCatSearchForm form) throws Exception {
		
		// 兼容原来的代码
		if(CheckUtils.isNotEmpty(form.getQ())) {
			form.setSearchText(form.getQ());
		}
		
		PageHolder<ProductVo> page = productService.getProductPage(form);

		DWebUtils.ajaxSucc(page);
	}


	@RequestMapping("/product/likeGood")
	public void likeGood(Long pid, boolean flag) throws Exception {

		
		Long userId = sessionHelper.getLoginUserId(true);
		
		productService.toggleLike(userId, pid, flag);

		DWebUtils.ajaxSucc();
	}


	@RequestMapping("/product/getLikePage")
	public void getLikePage(ProductCatSearchForm form) throws Exception {

		Long userId = sessionHelper.getLoginUserId(true);
				
		PageHolder<ProductVo> page = productService.getProductLikePage(userId, form);

		DWebUtils.ajaxSucc(page);
	}
	
}
