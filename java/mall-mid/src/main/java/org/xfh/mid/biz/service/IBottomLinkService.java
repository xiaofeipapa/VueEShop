package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.mid.db.po.BottomLink;

public interface IBottomLinkService extends ISingleTableOrderService<BottomLink>{
		
	void toggleShow(Long userId, Long id);
}
