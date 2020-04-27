package org.xfh.mid.biz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.mid.biz.service.IProductBrandService;
import org.xfh.mid.db.po.ProductBrand;

@Component
public class ProductBrandServiceImpl extends AbstractSingleTableService<ProductBrand> 
	implements IProductBrandService{
	
	private Logger logger = LoggerFactory.getLogger(ProductBrandServiceImpl.class);

	@Override
	protected void checkBeforeCreateOrUpdate(ProductBrand entity, boolean isCreate) throws LogicException {

		super.checkDuplicateByField("name", entity.getName(), "品牌的中文名字已经存在: " + entity.getName(), entity.getId(), isCreate);
		super.checkDuplicateByField("englishName", entity.getEnglishName(), "品牌的英文名字已经存在: " + entity.getEnglishName(), entity.getId(), isCreate);
		
	}
}
