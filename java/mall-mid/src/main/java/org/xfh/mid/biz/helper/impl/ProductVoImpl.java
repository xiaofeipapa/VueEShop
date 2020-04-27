package org.xfh.mid.biz.helper.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.mid.biz.helper.IProductHelper;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.web.WebProductVo;

@Component
public class ProductVoImpl implements IProductHelper {

    static final Logger logger = LoggerFactory.getLogger(ProductVoImpl.class);

	@Override
	public void convertLabelList(List<ProductVo> voList) {
		for(ProductVo vo : voList) {
			this.convertLabel(vo);
		}
	}

	@Override
	public void convertLabel(ProductVo vo) {
		if(CheckUtils.isNotEmpty(vo.getModalImages())) {
			vo.setProductImage(vo.getModalImages().split(",")[0]);
		}
	}

	@Override
	public void convertWebProducts(List<WebProductVo> voList) {
		for(WebProductVo vo : voList) {
			this.convertLabel(vo);
		}
	}

	@Override
	public void convertLabel(WebProductVo vo) {
		if(CheckUtils.isNotEmpty(vo.getModalImages())) {
			vo.setProductImage(vo.getModalImages().split(",")[0]);
		}
		
		if(CheckUtils.isEmpty(vo.getSaleStock())) {
			vo.setSaleStock(100);
		}
		
	}


}
