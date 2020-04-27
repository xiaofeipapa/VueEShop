package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xfh.dcore.dao.CommonDao;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.utils.AndFilter;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.helper.IOrderHelper;
import org.xfh.mid.biz.service.IOrderQueryService;
import org.xfh.mid.biz.service.web.IConfigService;
import org.xfh.mid.db.dao.ClientBuyerDao;
import org.xfh.mid.db.dao.PaymentLogQueryDao;
import org.xfh.mid.db.dao.StockDao;
import org.xfh.mid.db.dao.SupplierDao;
import org.xfh.mid.db.dao.UserOrderItemAllocInfoDao;
import org.xfh.mid.db.dao.UserOrderQueryDao;
import org.xfh.mid.db.dao.WarehouseQueryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.mid.db.po.UserOrder;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.BuyUserCats;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.ClientOrderFullDetailVo;
import org.xfh.mid.vo.PayCountdownVo;
import org.xfh.mid.vo.PaymentLogVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.UserOrderVo;
import org.xfh.mid.vo.index.AllocInfoPackageSearchForm;
import org.xfh.mid.vo.index.OrderIndexSearchForm;
import org.xfh.mid.vo.web.WebUserOrderIndexVo;

@Component
public class OrderQueryServiceImpl implements IOrderQueryService{

	private Logger logger = LoggerFactory.getLogger(OrderQueryServiceImpl.class);

	@Autowired
	protected CommonDao commonDao;

	@Autowired
	UserOrderQueryDao userOrderQueryDao;
	
	@Autowired
	IOrderHelper orderHelper;	
	
	@Autowired
	PaymentLogQueryDao paymentLogQueryDao;	
	
	@Autowired
	WarehouseQueryDao warehouseQueryDao;	
	
	@Autowired
	SupplierDao supplierDao;	
	
	@Autowired
	ClientBuyerDao creditDao;	
	
	@Autowired
	UserOrderItemAllocInfoDao UserOrderItemAllocInfoDao;
	
	@Autowired
	StockDao stockDao;	
    
    @Autowired
    IConfigService configService;
	
	@Autowired
	protected CommonDaoHelper daoHelper;
	
	@Value("${clientPayExpireMinutes}")
	int payCountdownMinute;
	
	@Override
	public PageHolder<UserOrderVo> getAllOrderPage(Long userId, OrderIndexSearchForm form) {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		String moreSql = "";
		if(CheckUtils.isNotEmpty(form.getBuyUserCat())) {
			moreSql += DSqlUtils.andEqString(DBs.BUY_USER_CAT, form.getBuyUserCat(), false);
		}
		
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[]{"buyerName","bizId", });
		filter.setOrderBy("CreateTime desc");

		int totalCount = userOrderQueryDao.getOrderCount(filter);
		List<UserOrderVo> dataList = userOrderQueryDao.getAllOrderPageList(filter);
		
		return this.getPager(userId, totalCount, dataList, filter);
	}

	@Override
	public PageHolder<UserOrderVo> getClientOrderPage(Long userId, OrderIndexSearchForm form) {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		String moreSql = DSqlUtils.andEqString("order_view."+DBs.BUY_USER_CAT, BuyUserCats.ClientBuyer.getValue(), false);
		
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[]{"order_view.bizId", "cb.name"});


		int totalCount = userOrderQueryDao.getClientOrderCount(filter);
		List<UserOrderVo> dataList = userOrderQueryDao.getClientOrderPageList(filter);
		orderHelper.convertLabelList(dataList);
		
		return this.getPager(userId, totalCount, dataList, filter);
	}

	private PageHolder<UserOrderVo> getPager(Long userId, int totalCount, List<UserOrderVo> dataList, 
			IndexSearchFilter searchFilter) {
		
		orderHelper.convertLabelList(dataList);
		
		PageHolder<UserOrderVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(searchFilter.getPageSize());
		page.setPageNo(searchFilter.getPageNo());

		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, searchFilter.getPageSize()));
				
		return page;
	}
	
	protected int calculatePageCount(int totalCount, int pageSize) {
		if(totalCount < pageSize) {
			return 1;
		}

	    int t = totalCount / pageSize;
	    if(totalCount % pageSize != 0) {
	    	t += 1;
	    }
	    return t;
	}

	@Override
	public ClientOrderFullDetailVo getClientOrderWithFullDetail(Long userId, String orderBizId) {
		
		UserOrderVo order = userOrderQueryDao.getOrderWithItemsByBizId(orderBizId);		
		orderHelper.convertLabel(order);
		
		// 获取客户信息
		ClientBuyerVo clientBuyer = creditDao.getClientFullDetail(order.getBuyUserId());
		orderHelper.convertLabel(clientBuyer);
		
		// 获取支付记录
		PaymentLogVo payment = paymentLogQueryDao.getPaymentLogByBizId(orderBizId);
		orderHelper.convertLabel(payment);
		
		// 获取调拨记录
		AndFilter filter = new AndFilter("pn." + DBs.ORDER_BIZ_ID, orderBizId, false);
		filter.andNe("pn." + DBs.DATA_STATUS, AllocInfoPackageStatus.TEMP.getValue(), false);		
		List<UserOrderItemAllocInfoPackageVo> plist = UserOrderItemAllocInfoDao.getPackageListOfOrder(filter.ok());
		orderHelper.convertLabel(plist);
		
		// 出库单信息和调拨记录是同一个表. 叫法不同而已(运营人员视图/仓管人员视图) 			
		// 但是!! 关联的allocInfo的信息不一样!! 详细见 xml里的getNeedPackageItems方法
		// 获取出库记录
		List<UserOrderItemAllocInfoPackageVo> finishList = new ArrayList<>();
		for(UserOrderItemAllocInfoPackageVo pvo : plist) {
			UserOrderItemAllocInfoPackageVo finishVo = new UserOrderItemAllocInfoPackageVo();
			RefUtils.copyFieldsToObject(pvo, finishVo);
			List<UserOrderItemAllocInfoVo> outMappingList = UserOrderItemAllocInfoDao.getNeedPackageItems(pvo.getSn());
			
			finishVo.setMappingList(outMappingList);
			finishList.add(finishVo);
		}
		
		ClientOrderFullDetailVo vo = new ClientOrderFullDetailVo();
		vo.setOrder(order);
		vo.setClientBuyer(clientBuyer);
		vo.setPayment(payment);
		vo.setPackageNoteList(plist);
		vo.setFinishList(finishList);
		
		return vo;
	}

	@Override
	public UserOrderVo getOrderWithItems(Long userId, String orderBizId) {

		UserOrderVo order = userOrderQueryDao.getOrderWithItemsByBizId(orderBizId);		
		orderHelper.convertLabel(order);
		
		return order;
	}

	@Override
	public UserOrderItemVo getOrderItemById(Long userId, Long itemId) {
		
		UserOrderItemVo itemVo = userOrderQueryDao.getItemByItemId(itemId);
		orderHelper.convertLabel(itemVo);
				
		return itemVo;
	}

	@Override
	public List<UserOrderItemAllocInfoVo> getAllocListByItemId(Long userId, Long itemId) {
		return UserOrderItemAllocInfoDao.getAllocInfoListByItemId(itemId);
	}

	@Override
	public PageHolder<UserOrderItemAllocInfoPackageVo> getPackagePage(Long userId,
			AllocInfoPackageSearchForm form) {

		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));
		
		String moreSql = DSqlUtils.andNe("pn." + DBs.DATA_STATUS, AllocInfoPackageStatus.TEMP.getValue(), false);
		filter.setMoreSql(moreSql);
		filter.setOrderBy("CreateTime desc");

		int totalCount = UserOrderItemAllocInfoDao.getPackageCount(filter);
		List<UserOrderItemAllocInfoPackageVo> dataList = UserOrderItemAllocInfoDao.getPackageIndexPageList(filter);
		
		PageHolder<UserOrderItemAllocInfoPackageVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(DSqlUtils.calculatePageCount(totalCount, filter.getPageSize()));
		
		return page;
	}

	@Override
	public UserOrderItemAllocInfoPackageVo getPackageDetail(Long userId, Long id) {
		Map data = commonDao.getById(DBs.TABLE_USER_ORDER_ITEM_ALLOC_INFO_PACKAGE, id);		
		UserOrderItemAllocInfoPackageVo vo = RefUtils.copyFromMapToObject(data, UserOrderItemAllocInfoPackageVo.class, false, null);
		
		List<UserOrderItemAllocInfoVo> infoList = UserOrderItemAllocInfoDao.getNeedPackageItems(vo.getSn());
		
		DeliverAddress addressData = userOrderQueryDao.getGoodAddressByBizId(vo.getOrderBizId());
		
		vo.setMappingList(infoList);
		vo.setAddressData(addressData);
		
		return vo;
	}

	@Override
	public Integer getWarehouseStock(Long userId, Long warehouseId, Long productId) {
		return stockDao.getWarehouseStock(warehouseId, productId);
	}

	@Override
	public PayCountdownVo getOrderCountdownInfo(Long userId,  String bizId) {
		String sql = DSqlUtils.andEqString(DBs.BIZ_ID, bizId, false);
		sql += DSqlUtils.andEqNumber(DBs.BUY_USER_ID, userId, false);
		UserOrder order = daoHelper.getOneBySql(UserOrder.class, sql);
		
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(order.getCreateTime());
		
		nowTime.add(Calendar.MINUTE, payCountdownMinute);
		
		PayCountdownVo vo = new PayCountdownVo();
		
		vo.setBizId(bizId);
		vo.setTotalShouldPaid(order.getTotalShouldPaid());
		vo.setEndTime(DateUtils.format(nowTime.getTime(), "YYYY-MM-dd hh:mm:ss"));
		vo.setConfig(configService.getConfig());
		
		return vo;
	}

	@Override
	public WebUserOrderIndexVo getUserOrders(Long userId, OrderIndexSearchForm form) {
		
		form.setFront(true);
		PageHolder<UserOrderVo> page = this.getOrderPageWithItems(userId, form);

		WebUserOrderIndexVo vo = new WebUserOrderIndexVo();
//		orderHelper.convertLabelList(page.getDataList());
		vo.setDataList(page.getDataList());
		vo.setPageCount(page.getPageCount());
		
		// 查找待支付订单
		String sql = DSqlUtils.andEqNumber(DBs.BACK_USER_ID, userId, false);
		sql += DSqlUtils.andEqString(DBs.DATA_STATUS, UserOrderStatus.NEW.getValue(), false);
		int count = daoHelper.getCountBySql(UserOrder.class, sql);
		
		vo.setWaitPaidNum(count);
		
		// 查找等候签收订单
		sql = DSqlUtils.andEqNumber(DBs.BACK_USER_ID, userId, false);
		Object[] values = this.toReceiveForFrontUser();		
		sql += DSqlUtils.andInValues(DBs.DATA_STATUS, values);
		count = daoHelper.getCountBySql(UserOrder.class, sql);
		
		vo.setWaitAcceptNum(count);
		
		return vo;
	}
	
	// 对于前台用户来说, 可视作"等候签收"的内部状态
	private Object[] toReceiveForFrontUser() {

		Object[] values = new String[] {
				UserOrderStatus.PAID.getValue()
				,UserOrderStatus.INNER_PACKAGE.getValue()
				,UserOrderStatus.INNER_WAIT_CAR.getValue()
				,UserOrderStatus.SEND_CAR.getValue()
		};
		
		return values;
	}

	@Override
	public PageHolder<UserOrderVo> getOrderPageWithItems(Long userId, OrderIndexSearchForm form) {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		String moreSql = "";
		if(CheckUtils.isNotEmpty(form.getBuyUserCat())) {
			moreSql += DSqlUtils.andEqString(DBs.BUY_USER_CAT, form.getBuyUserCat(), false);
		}
		
		// 查询前台用户订单
		if(form.isFront()) {
			moreSql += DSqlUtils.andEqNumber(DBs.BUY_USER_ID, userId, false);
			
			int searchCat = Integer.parseInt(form.getSearchStatus());

			// 待付款订单
			if(searchCat == OrderIndexSearchForm.SHOW_TO_PAY) {
				moreSql += DSqlUtils.andEqString(DBs.DATA_STATUS, UserOrderStatus.NEW.getValue(), false);
			}

			// 待签收订单
			if(searchCat == OrderIndexSearchForm.SHOW_TO_GET) {
				// 内部状态对外一律显示等候签收
				Object[] values = this.toReceiveForFrontUser();
				
				moreSql += DSqlUtils.andInValues(DBs.DATA_STATUS, values);
				
			}
		}
		
		filter.setMoreSql(moreSql);
		
		filter.setLikeKeys(new String[]{"buyerName","bizId", });
		filter.setOrderBy("CreateTime desc");

		int totalCount = userOrderQueryDao.getOrderCount(filter);
		List<UserOrderVo> dataList = userOrderQueryDao.getOrderPageWithItems(filter);
		
		return this.getPager(userId, totalCount, dataList, filter);
	}
	
}
