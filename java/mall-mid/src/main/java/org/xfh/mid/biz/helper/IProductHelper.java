package org.xfh.mid.biz.helper;

import java.util.List;

import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.web.WebProductVo;

/**
 * 封装不涉及数据库的业务代码. 
 * 
 * @author cys
 *
 */
public interface IProductHelper {
	
	void convertLabelList(List<ProductVo> voList);
	
	void convertLabel(ProductVo vo);

	
	void convertWebProducts(List<WebProductVo> voList);
	
	void convertLabel(WebProductVo vo);
}
