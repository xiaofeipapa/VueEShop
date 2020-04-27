package org.xfh.mid.biz.orderService;

import org.xfh.mid.vo.form.ProductOutFinishPackageForm;

/**
 * 1. 根据调拨信息拣货(物理操作)
 * 2. 更新物流发车记录
 * 3. 更新订单状态
 * 
 * @author cys
 *
 */
public interface IOrderMakePackageOut{
	
	// 更新出库单为发出状态
	void sendPackage(Long backUserId, ProductOutFinishPackageForm form)throws Exception;

}
