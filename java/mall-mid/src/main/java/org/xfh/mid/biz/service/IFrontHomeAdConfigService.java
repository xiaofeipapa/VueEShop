package org.xfh.mid.biz.service;

import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.mid.db.po.FrontHomeAdConfig;

public interface IFrontHomeAdConfigService extends ISingleTableOrderService<FrontHomeAdConfig>{
		
	void toggleShow(Long userId, Long id)throws LogicException;
	
	void deleteData(Long userId, Long id) throws LogicException;
}
