package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.WarehousePos;
import org.xfh.mid.vo.WarehousePosVo;

public interface IWarehousePosService  extends ISingleTableService<WarehousePos>{
	
	PageHolder<WarehousePosVo> getWarehousePosPage(IndexSearchFilter searchFilter);
}
