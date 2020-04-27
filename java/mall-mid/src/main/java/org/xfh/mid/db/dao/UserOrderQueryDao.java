package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.UserOrderVo;

/**
 * 查询订单相关
 * 
 * @author cys
 *
 */
public interface UserOrderQueryDao {	
	
	// 返回订单总数
	int getOrderCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回分页数据
	List<UserOrderVo> getAllOrderPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回企业用户分页数据
	List<UserOrderVo> getClientOrderPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回企业用户订单总数
	int getClientOrderCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回订单信息
	UserOrderVo getOrderByBizId(@Param("bizId")String bizId);
	
	// 返回订单和关联商品信息
	UserOrderVo getOrderWithItemsByBizId(@Param("bizId")String bizId);
	
	// 根据订单id, 返回用户的收货地址
	DeliverAddress getGoodAddressByBizId(@Param("bizId")String bizId);

	// 根据订单id, 获取订单关联的项
	List<UserOrderItemVo> getItemListByBizId(@Param("bizId")String bizId);
	
	// 根据订单项id, 获取订单项信息
	UserOrderItemVo getItemByItemId(@Param("itemId")Long itemId);
	
	// 返回带订单项的分页数据
	List<UserOrderVo> getOrderPageWithItems(@Param("searchFilter")IndexSearchFilter searchFilter);
	
}
