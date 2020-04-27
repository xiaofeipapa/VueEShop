package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.mid.vo.AddressVo;

public interface IDeliverAddressService  extends ISingleTableService<DeliverAddress>{
	
	// 返回默认的地址
	AddressVo getDefaultAddress(Long userId, Long addressUserId, String addressUserCat);
}
