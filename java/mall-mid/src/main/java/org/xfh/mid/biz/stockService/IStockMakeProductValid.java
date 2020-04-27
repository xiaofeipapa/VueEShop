package org.xfh.mid.biz.stockService;

/**
 * 新增入库批次的时候, 如果提交数据, 那么会统计入库的商品总数, 变成正式的商品仓库库存. 
 * @author cys
 *
 */
public interface IStockMakeProductValid {
	
	void makeProductInBatchValid(Long userId, Long inId) throws Exception;
}
