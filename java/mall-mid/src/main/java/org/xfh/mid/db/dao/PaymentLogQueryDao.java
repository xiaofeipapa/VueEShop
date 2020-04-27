package org.xfh.mid.db.dao;

import org.apache.ibatis.annotations.Param;
import org.xfh.mid.vo.PaymentLogVo;

/**
 * 支付记录相关
 * 
 * @author cys
 *
 */
public interface PaymentLogQueryDao {	
		
	// 返回支付记录
	PaymentLogVo getPaymentLogByBizId(@Param("bizId")String bizId);
	
}
