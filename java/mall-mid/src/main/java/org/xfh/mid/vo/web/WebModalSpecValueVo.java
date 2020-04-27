package org.xfh.mid.vo.web;

import org.xfh.mid.db.po.ModalSpecValue;

/**
 * 商品模型的规格属性组, 规格属性列表
 * @author cys
 *
 */
public class WebModalSpecValueVo extends ModalSpecValue{
	
	boolean check = false;		// 是否选择

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}	

}
