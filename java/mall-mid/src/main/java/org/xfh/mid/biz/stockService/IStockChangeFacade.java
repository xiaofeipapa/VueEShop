package org.xfh.mid.biz.stockService;

/**
 * 以下业务会改变商品的库存: 
 * 1) 入库管理 / 正常入库
 * 2) 盘点库存 
 * 3) 盘点报损
 * 4) 出库
 * 5) 退换货入库
 * 6) 后台下单购买
 * 
 * 为了保证代码/事务可控, 将它们都放到这个接口里. 
 * @author cys
 *
 */
public interface IStockChangeFacade extends IStockMakeProductValid, IStockMakeCheckActivityValid, 
IStockMakeOrder{

}
