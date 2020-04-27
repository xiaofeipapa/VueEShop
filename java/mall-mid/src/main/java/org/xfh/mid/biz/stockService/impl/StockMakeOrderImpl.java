package org.xfh.mid.biz.stockService.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.helper.IOrderHelper;
import org.xfh.mid.biz.stockService.IStockMakeOrder;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.dao.UserOrderDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.Product;
import org.xfh.mid.db.po.UserOrder;
import org.xfh.mid.db.po.UserOrderItem;
import org.xfh.mid.enums.BuyUserCats;
import org.xfh.mid.enums.OrderSources;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.BatchOptResult;
import org.xfh.mid.vo.ProductVo;
import org.xfh.mid.vo.SaleStockVo;
import org.xfh.mid.vo.form.MakeOrderForm;
import org.xfh.mid.vo.form.UserBuyProduct;

@Component
public class StockMakeOrderImpl extends AbstractStockService implements IStockMakeOrder{

	private Logger logger = LoggerFactory.getLogger(StockMakeOrderImpl.class);

	@Autowired
	ProductQueryDao productQueryDao;

	@Autowired
	UserOrderDao userOrderDao;
	
	@Autowired
	IOrderHelper orderHelper;	
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BatchOptResult makeOrderForFront(Long userId, MakeOrderForm form) throws Exception {

		return this.internalMake(userId, form, true);
		
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BatchOptResult makeOrderForBack(Long backUserId, MakeOrderForm form) throws Exception {
				
		return this.internalMake(backUserId, form, false);		
	}


	@Transactional
	private BatchOptResult internalMake(Long userId, MakeOrderForm form, boolean isFront) throws Exception {
				
		long startTime = System.currentTimeMillis();   //获取开始时间
		
		BatchOptResult result = new BatchOptResult();
		
		// 检查输入
		this.checkInput(form, result, isFront, userId);
		
		if( ! result.isSucc()) {
			return result;
		}
		
		List<Long> pids = new ArrayList<>();

		for(UserBuyProduct up : form.getProducts()) {
			pids.add(up.getId());
		}
		
		// 检查库存
		this.checkStock(form, pids, result);
		
		if( ! result.isSucc()) {
			return result;
		}
		
		Date now = new Date();
		
		// 实际保存订单数据. 
		UserOrder order = new UserOrder();
		
		if(isFront) {
			order.setBuyUserId(userId);
			order.setBuyUserCat(BuyUserCats.FrontUser.getValue());
			order.setSource(OrderSources.FROM_WEB.getValue());
		}else {
			order.setBuyUserId(form.getClientId());
			order.setBuyUserCat(BuyUserCats.ClientBuyer.getValue());
			order.setBackUserId(userId);				
			order.setSource(OrderSources.FROM_BACK.getValue());	
		}
		order.setCreateTime(now);
		
		String bizId = "order-" + System.currentTimeMillis();		
		order.setBizId(bizId);		
		order.setDataStatus(UserOrderStatus.NEW.getValue());
		
		order.setDeliveryFee(form.getDeliveryFee());
		order.setTotalProductFee(form.getTotalProductFee());
		
		if(form.getDeliveryFee() == null) {
			order.setTotalShouldPaid(form.getTotalProductFee());			
		}
		else {
			order.setTotalShouldPaid(form.getTotalProductFee().add(form.getDeliveryFee()));	
		}
		
		// FIXME 缺发票信息
		order.setGoodAddressId(form.getGoodAddressId());
		order.setOrderRemark(form.getOrderRemark());
		
		// FIXME 计算快递费用
		
		commonDao.insertOne(DBs.TABLE_USER_ORDER, RefUtils.copyFieldsToMap(order, false, null, null));
		
		String sql = DSqlUtils.andInValues("id", pids);
		List<ProductVo> productList = productQueryDao.getProductVoBySql(sql);
		
		List<UserOrderItem> itemList = new ArrayList<>();
		for(ProductVo prod : productList) {
			
			UserOrderItem item = new UserOrderItem();
			item.setOrderBizId(order.getBizId());
			item.setParentBizId(null);
			item.setProductId(prod.getId());
			item.setModalId(prod.getModalId());
			item.setModalTitle(prod.getModalTitle());
			item.setSpecValueString(prod.getSpecValueString());
			
			// 理论上应该所有商品都有图片的
			item.setImagePath(prod.getModalImages().split(",")[0]);
			item.setBrandId(prod.getBrandId());
			item.setMarketPrice(prod.getMarketPrice());
			item.setRetailPrice(prod.getRetailPrice());

			if(isFront) {
				item.setBuyUserId(userId);				
				item.setBuyUserCat(BuyUserCats.FrontUser.getValue());	
			}else {
				item.setBuyUserId(form.getClientId());		
				item.setBuyUserCat(BuyUserCats.ClientBuyer.getValue());		
			}
			item.setSku(prod.getSku());
			
			
			for(UserBuyProduct up : form.getProducts()) {
				
				if(up.getId().longValue() == prod.getId().longValue()) {
					item.setQuantity(up.getCount());
					item.setTotalProductFee(prod.getRetailPrice().multiply(new BigDecimal(up.getCount())));
				}
			}
			
			itemList.add(item);
		}
		
		// 批量插入
		userOrderDao.batchCreateOrderItem(itemList);
		
		result.setBizId(order.getBizId());

		 long endTime=System.currentTimeMillis(); //获取结束时间
		 String msg = "###########################";
		 msg += "-------- 创建订单成功, 运行总时间: " +  (endTime-startTime) / 1000 + " s";
		 msg += "###########################";
		 logger.info(msg);
		
		return result;
		
	}
	
	
	
	// 检查输入
	private void checkInput(MakeOrderForm form, BatchOptResult result, boolean isFront, Long userId) throws LogicException {
		
		if(form.getProducts().size() < 1) {
			throw new LogicException("此订单没有商品, 服务器拒绝! ");
		}
		
		// 后台下单需要输入客户id和快递费用
		if( ! isFront) {
			VUtils.checkM(form.getClientId(), "请选择客户");		
			VUtils.checkM(form.getDeliveryFee(), "请输入快递费用");
		}

		VUtils.checkM(form.getGoodAddressId(), "请选择收货地址");	
		VUtils.checkBigDecimalMustGtZero(form.getTotalProductFee(), "商品的总价格不可能是0, 服务器拒绝! ");

		for(UserBuyProduct up : form.getProducts()) {				
			
			if(up.getCount() < 1) {
				result.addError(up.getId(), "商品的购买数量不能小于1");
			}
						
		}
	}

	@Transactional
	private void checkStock(MakeOrderForm form, List<Long> pids, BatchOptResult result) {
		// 获取商品销售库存
		String sql = DSqlUtils.andInValues(DBs.PRODUCT_ID, pids);
		List<SaleStockVo> stockList = stockDao.getSaleStockBySql(sql);
		
		// 检查虚拟库存
		for(SaleStockVo stockVo : stockList) {
			Long productId = stockVo.getProductId();
			int totalStock = stockVo.getStock();
			
			for(UserBuyProduct up : form.getProducts()) {
				if(up.getId().longValue() == productId.longValue()) {
					
					// 检查库存
					if(stockVo.getStockCat() == Product.STOCK_IS_MOCK) {
						// 虚拟不需要检查库存
						continue;
					}
					
					int count = up.getCount();
					
					if(count > totalStock) {
						result.addError(productId, "库存不足,请调整购买数量. 商品库存: " + totalStock + ", 购买数: " + count);	
						continue;
					}				
					
					// 减去库存
					TreeMap<String, Object> param = new TreeMap<>();
					param.put(DBs.STOCK, totalStock - count);
					sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, up.getId(), true);
					commonDao.updateBySql(DBs.TABLE_SALE_STOCK, param, sql);
				}
			}
			
		}
		
	}
	

}
