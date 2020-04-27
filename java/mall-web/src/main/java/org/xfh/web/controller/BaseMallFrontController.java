package org.xfh.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.utils.NumUtils;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.service.IBackLogService;
import org.xfh.mid.biz.service.IBackMenuService;
import org.xfh.mid.biz.service.IBackRoleMenuService;
import org.xfh.mid.biz.service.IBackRoleService;
import org.xfh.mid.biz.service.IBackUserService;
import org.xfh.mid.biz.service.IDistrictDataService;
import org.xfh.mid.biz.service.IProductBrandService;
import org.xfh.mid.biz.service.IProductCategoryService;
import org.xfh.mid.biz.service.IProductParamService;
import org.xfh.mid.biz.service.IProductQueryService;
import org.xfh.mid.biz.service.IWarehousePosService;
import org.xfh.mid.biz.service.IWarehouseService;
import org.xfh.mid.db.po.BackLog;
import org.xfh.web.config.WebSessionHelper;
import org.xfh.web.vo.WebUserSessionVo;

public abstract class BaseMallFrontController {

    static final Logger logger = LoggerFactory.getLogger(BaseMallFrontController.class);
    	
	@Resource
	protected WebSessionHelper sessionHelper;
		
	protected boolean isCreate(Long pk) {
		return pk == null || pk.intValue() < 1;
	}

	
}
