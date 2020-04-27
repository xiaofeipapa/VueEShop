package org.xfh.web.vo;

import java.util.Collection;
import java.util.List;

import org.xfh.mid.db.po.FrontHomeAdConfig;
import org.xfh.mid.vo.ProductCategoryVo;

public class WebTopNav {

	List<FrontHomeAdConfig> adList;
	
	Collection<ProductCategoryVo> catList;		// 分类列表

	public List<FrontHomeAdConfig> getAdList() {
		return adList;
	}

	public void setAdList(List<FrontHomeAdConfig> adList) {
		this.adList = adList;
	}

	public Collection<ProductCategoryVo> getCatList() {
		return catList;
	}

	public void setCatList(Collection<ProductCategoryVo> catList) {
		this.catList = catList;
	}
	
	
}
