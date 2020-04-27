package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.mid.db.po.UserOrderItem;

/**
 * 订单处理
 * 
 * @author cys
 *
 */
public interface UserOrderDao {	

	void batchCreateOrderItem(@Param("dataList")List<UserOrderItem> dataList);
	
}
