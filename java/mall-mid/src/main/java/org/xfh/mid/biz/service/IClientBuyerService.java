package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.Buyer;

public interface IClientBuyerService extends ISingleTableService<Buyer>{
	
	void updateStatus(Long userId, Long id, String opt) throws Exception;
	
}
