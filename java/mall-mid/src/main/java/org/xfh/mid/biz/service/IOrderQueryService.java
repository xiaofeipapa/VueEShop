package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.ClientOrderFullDetailVo;
import org.xfh.mid.vo.PayCountdownVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.UserOrderVo;
import org.xfh.mid.vo.index.AllocInfoPackageSearchForm;
import org.xfh.mid.vo.index.OrderIndexSearchForm;
import org.xfh.mid.vo.web.WebUserOrderIndexVo;

/**
 * 订单查询类
 * 
 * @author cys
 *
 */
public interface IOrderQueryService {
	
	// 返回订单分页, 包含普通用户, 企业用户
	PageHolder<UserOrderVo> getAllOrderPage(Long userId, OrderIndexSearchForm form);
	
	// 返回企业用户订单分页
	PageHolder<UserOrderVo> getClientOrderPage(Long userId, OrderIndexSearchForm form);
	
	// 返回企业用户订单细节, 包括企业用户信息, 信用借还记录
	ClientOrderFullDetailVo getClientOrderWithFullDetail(Long userId, String orderBizId);
	
	// 返回企业用户订单细节和关联项
	UserOrderVo getOrderWithItems(Long userId, String orderBizId);
	
	// 返回调拨所需要的信息
	UserOrderItemVo getOrderItemById(Long userId, Long itemId);
	
	// 返回订单项的调拨信息
	List<UserOrderItemAllocInfoVo> getAllocListByItemId(Long userId, Long itemId);
	
	// 返回出库单列表页数据
	PageHolder<UserOrderItemAllocInfoPackageVo> getPackagePage(Long userId, AllocInfoPackageSearchForm searchForm);
	
	// 返回出库单关联的各种细节
	UserOrderItemAllocInfoPackageVo getPackageDetail(Long userId, Long id);
	
	Integer getWarehouseStock(Long userId, Long warehouseId, Long productId);
	
	// 付款之后, 返回倒计时信息, 通常用于订单成功页
	PayCountdownVo getOrderCountdownInfo(Long userId, String bizId);
	
	// 用户中心 / 我的订单
	WebUserOrderIndexVo getUserOrders(Long userId, OrderIndexSearchForm form);
	
	PageHolder<UserOrderVo> getOrderPageWithItems(Long userId, OrderIndexSearchForm form);

}
