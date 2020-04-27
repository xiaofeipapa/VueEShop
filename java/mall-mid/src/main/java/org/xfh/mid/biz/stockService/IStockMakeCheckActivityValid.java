package org.xfh.mid.biz.stockService;

/**
 * 
 * 新增盘点的时候, 如果提交数据, 那么会重新计算商品的仓库库存. 
 * 
 * @author cys
 *
 */
public interface IStockMakeCheckActivityValid {
	
	void makeCheckActivityValid(Long userId, Long activityId) throws Exception;
}
