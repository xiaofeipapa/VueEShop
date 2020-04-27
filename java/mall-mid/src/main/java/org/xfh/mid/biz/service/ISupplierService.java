package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.mid.db.po.Supplier;

public interface ISupplierService extends ISingleTableService<Supplier>{
	void updateStatus(Long userId, Long id, String opt) throws Exception;
}
