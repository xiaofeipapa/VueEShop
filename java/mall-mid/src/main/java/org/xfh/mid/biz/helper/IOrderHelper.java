package org.xfh.mid.biz.helper;

import java.util.Date;
import java.util.List;

import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.PaymentLogVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.UserOrderVo;

/**
 * 封装不涉及数据库的业务代码. 
 * 
 * @author cys
 *
 */
public interface IOrderHelper {
	
	void convertLabelList(List<UserOrderVo> voList);
	
	void convertLabel(UserOrderVo vo);
	
	void convertLabel(ClientBuyerVo vo);
	
	void convertLabel(PaymentLogVo vo);
	
	void convertLabel(UserOrderItemVo vo);
	
	void convertLabel(UserOrderItemAllocInfoPackageVo vo);
	
	void convertLabel(List<UserOrderItemAllocInfoPackageVo> voList);
	
	boolean checkExpire(Date orderCreateTime);
}
