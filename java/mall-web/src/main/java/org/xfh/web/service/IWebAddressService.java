package org.xfh.web.service;

import java.util.List;

import org.xfh.dcore.ex.LogicException;
import org.xfh.mid.db.po.DeliverAddress;

public interface IWebAddressService {

	Long saveAddress(Long userId, DeliverAddress da)throws LogicException;
	
	// 设置默认的地址
	void setDefaultAddress(Long userId, Long id, String cat);
	
	// 返回用户的地址列表
	List<DeliverAddress> getAddressList(Long userId, String cat);
	
	List<DeliverAddress> deleteIt(Long userId, Long id);
}
