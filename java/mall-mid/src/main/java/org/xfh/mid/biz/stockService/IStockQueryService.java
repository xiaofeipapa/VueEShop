package org.xfh.mid.biz.stockService;

import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.WarehouseStockVo;
import org.xfh.mid.vo.index.StockIndexSearchForm;

public interface IStockQueryService {
	
	// 返回仓库库存分页数据
	PageHolder<WarehouseStockVo> getWarehouseStockPage(Long userId, StockIndexSearchForm form);
}
