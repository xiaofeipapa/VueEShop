package org.xfh.web.controller.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.mid.vo.web.WebProductVo;
import org.xfh.web.controller.BaseMallFrontController;
import org.xfh.web.service.IWebProductService;
import org.xfh.web.vo.WebShopcartForm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class WebShopcartController extends BaseMallFrontController {
	
    static final Logger logger = LoggerFactory.getLogger(WebShopcartController.class);

    @Autowired
    IWebProductService productService;

    // 获取商品的状态
	@RequestMapping("/shopcart/checkProducts")
	public void checkProducts(String jsonData) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(false);
		
		WebShopcartForm form = new Gson().fromJson(jsonData, new TypeToken<WebShopcartForm>() {}.getType());
		
		// 返回商品的状态
		List<WebProductVo> voList = productService.getProductByIds(userId, form.getPids());

		DWebUtils.ajaxSucc(voList);
		
	}

	
}
