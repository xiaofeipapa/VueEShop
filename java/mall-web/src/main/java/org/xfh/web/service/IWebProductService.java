package org.xfh.web.service;

import java.util.List;

import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.web.ProductCatSearchForm;
import org.xfh.mid.vo.web.WebProductVo;
import org.xfh.web.vo.ModalSpecMapVo;

public interface IWebProductService {

	PageHolder<ProductVo> getProductPage(ProductCatSearchForm form);
	
	WebProductVo getWebProductDetail(Long userId, Long productId);
	
	Long getProductIdBySpec(Long userId, ModalSpecMapVo form);
	
	List<WebProductVo> getProductByIds(Long userId, List<Long> ids);
	
	// 切换收藏
	void toggleLike(Long userId, Long productId, boolean flag);

	// 获取收藏分页
	PageHolder<ProductVo> getProductLikePage(Long userId, ProductCatSearchForm form);
	
}
