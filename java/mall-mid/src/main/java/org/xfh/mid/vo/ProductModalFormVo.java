package org.xfh.mid.vo;

import java.io.Serializable;
import java.util.List;

import org.xfh.dcore.utils.RefUtils;
import org.xfh.mid.db.po.ProductBrand;
import org.xfh.mid.db.po.ProductModal;
import org.xfh.mid.db.po.ProductParam;

/**
 * 编辑模型数据时候的form表单
 * 
 * @author cys
 *
 */
public class ProductModalFormVo extends ProductModal implements Serializable{
	

	private static final long serialVersionUID = 4161355409910763498L;

	public static ProductModalFormVo copyFrom(ProductModal modal) {
		ProductModalFormVo dto = new ProductModalFormVo();
		RefUtils.copyFieldsToObject(modal, dto);
		return dto;
	}
		
	ProductBrand brand;
	Level3DataVo childCatVo1;
	Level3DataVo childCatVo2;
	Level3DataVo childCatVo3;
	
	String paramStr;			//  其他参数值
	
	List<ProductParam> paramList;

	public List<ProductParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<ProductParam> paramList) {
		this.paramList = paramList;
	}

	public ProductBrand getBrand() {
		return brand;
	}

	public void setBrand(ProductBrand brand) {
		this.brand = brand;
	}

	public Level3DataVo getChildCatVo1() {
		return childCatVo1;
	}

	public void setChildCatVo1(Level3DataVo childCatVo1) {
		this.childCatVo1 = childCatVo1;
	}

	public Level3DataVo getChildCatVo2() {
		return childCatVo2;
	}

	public void setChildCatVo2(Level3DataVo childCatVo2) {
		this.childCatVo2 = childCatVo2;
	}

	public Level3DataVo getChildCatVo3() {
		return childCatVo3;
	}

	public void setChildCatVo3(Level3DataVo childCatVo3) {
		this.childCatVo3 = childCatVo3;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

}
