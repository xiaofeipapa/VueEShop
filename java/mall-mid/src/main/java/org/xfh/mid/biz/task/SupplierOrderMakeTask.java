package org.xfh.mid.biz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 生成供应商订单记录, 具体设计见 <供应商功能的业务设计> 文档
 * 
 * @author cys
 *
 */
@Component
public class SupplierOrderMakeTask {

	// 每天上午10点, 下午16点执行
	@Scheduled(cron="*0 0 10,16 ?")
	public void runEveryDay() {
		
	}
	
	private void makeOrders() {
		
	}
}
