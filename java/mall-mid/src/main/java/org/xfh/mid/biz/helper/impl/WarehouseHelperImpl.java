package org.xfh.mid.biz.helper.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.mid.biz.helper.IWarehouseHelper;
import org.xfh.mid.enums.CheckResultCats;
import org.xfh.mid.vo.UserCheckProductVo;

@Component
public class WarehouseHelperImpl implements IWarehouseHelper {

    static final Logger logger = LoggerFactory.getLogger(WarehouseHelperImpl.class);

	@Override
	public void convertLabelList(List<UserCheckProductVo> voList) {
		for(UserCheckProductVo vo : voList) {
			this.convertLabel(vo);
		}
	}

	@Override
	public void convertLabel(UserCheckProductVo vo) {
		vo.setCheckResultCatLabel(CheckResultCats.getLabel(vo.getCheckResultCat()));
	}


}
