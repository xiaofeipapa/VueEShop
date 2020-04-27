package org.xfh.mid.biz.helper.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.mid.biz.helper.IOrderHelper;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.ClientGrades;
import org.xfh.mid.enums.LogisticsCats;
import org.xfh.mid.enums.OrderSources;
import org.xfh.mid.enums.PartnerLogisticsPayCats;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.PaymentLogVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemVo;
import org.xfh.mid.vo.UserOrderVo;

@Component
public class OrderHelperImpl implements IOrderHelper {

    static final Logger logger = LoggerFactory.getLogger(OrderHelperImpl.class);
    
	@Value("${clientPayExpireMinutes}")
	int payCountdown;				// 付款超时分钟数

	@Override
	public void convertLabelList(List<UserOrderVo> voList) {

		for(UserOrderVo vo : voList) {
			
			this.convertLabel(vo);

		}
		
	}

	@Override
	public void convertLabel(UserOrderVo vo) {
		
		this.setStatusLabel(vo);
		vo.setSourceLabel(OrderSources.getLabel(vo.getSource()));
		vo.setCreateTimeStr(DateUtils.formatDBTime(vo.getCreateTime()));
//		this.setStatusLabelForFront(vo);
		
		this.setPayTimeIfNew(vo);
	}
	
	private void setStatusLabel(UserOrderVo vo) {
		
		String status = vo.getDataStatus();
		if(status == null)return;
		
		vo.setBackStatusLabel(UserOrderStatus.getLabel(status));
		if( ! status.startsWith("INNER")) {
			vo.setFrontStatusLabel(status);
			return;
		}
		
		// 隐藏内部状态
		boolean innerPick = UserOrderStatus.INNER_PACKAGE.getValue().equalsIgnoreCase(status);			// 拣货
		boolean innerWait = UserOrderStatus.INNER_WAIT_CAR.getValue().equalsIgnoreCase(status);		// 等待物流
		if(innerPick || innerWait) {
			vo.setFrontStatusLabel("等待发货");
			return;
		}
		
		logger.error("状态没有设置正确, status: " + status);
	}
	
//	private void setStatusLabelForFront(UserOrderVo vo) {
//		
//		String status = vo.getDataStatus();
//		boolean canComment = UserOrderStatus.contains(status, UserOrderStatus.RECEIVE
//				, UserOrderStatus.USER_CANCEL, UserOrderStatus.USER_REFUSE);
//		vo.setShowComment(canComment);
//		
//		boolean showAccept = UserOrderStatus.contains(status, UserOrderStatus.PAID, 
//				UserOrderStatus.INNER_PACKAGE
//				, UserOrderStatus.INNER_WAIT_CAR
//				, UserOrderStatus.SEND_CAR
//				);
//		vo.setShowAccept(showAccept);
//	}

	private void setPayTimeIfNew(UserOrderVo vo) {

		if( ! UserOrderStatus.NEW.getValue().equalsIgnoreCase(vo.getDataStatus())) {
			return;
		}
			
		// 应该由定时任务设置超时. 这里只是设置显示状态. 

		// 检查时间大小
		GregorianCalendar endTime=new GregorianCalendar();
		endTime.setTime(vo.getCreateTime());
		endTime.add(GregorianCalendar.MINUTE, payCountdown);
		
		// 由于存在付款倒数, 所以这里要设置好时区. 
		Date now = DateUtils.getChinaDate();
		if(now.getTime() > endTime.getTimeInMillis()) {
			vo.setDataStatus(UserOrderStatus.PAY_EXPIRE.getValue());
			this.setStatusLabel(vo);
			return;
		}
				
		// createTime 时间延后20分钟. 
		String dstr = DateUtils.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss");
		vo.setPayEndTime(dstr);
		
	}
	
	// 检查订单是否支付超时
	public boolean checkExpire(Date orderCreateTime) {

		GregorianCalendar endTime=new GregorianCalendar();
		endTime.setTime(orderCreateTime);
		endTime.add(GregorianCalendar.MINUTE, payCountdown);

		// 由于存在付款倒数, 所以这里要设置好时区. 
		Date now = DateUtils.getChinaDate();
		return now.getTime() > endTime.getTimeInMillis();
		
	}

	@Override
	public void convertLabel(ClientBuyerVo vo) {
		
		vo.setGradeLabel(ClientGrades.getLabel(vo.getGrade()));
		if(vo.getUseCreditAmount() != null) {
			vo.setRemainAmount(vo.getCreditAmount().subtract(vo.getUseCreditAmount()));			
		}else {
			vo.setRemainAmount(vo.getCreditAmount());
			vo.setUseCreditAmount(new BigDecimal(0));
		}
	}

	@Override
	public void convertLabel(PaymentLogVo vo) {
		if(vo == null) {
			return;
		}
		
		if(vo.getPayCat() != null) {
			vo.setPayCatLabel(PayCats.getLabel(vo.getPayCat()));			
		}
		if(vo.getPayTime() != null) {
			vo.setPayTimeStr(DateUtils.formatDBTime(vo.getPayTime()));			
		}
		if("Y".equalsIgnoreCase(vo.getSuccFlag())) {
			vo.setSuccLabel("成功");
		}
		else {
			vo.setSuccLabel("失败");
		}
	}

	@Override
	public void convertLabel(UserOrderItemVo vo) {

	}

	@Override
	public void convertLabel(UserOrderItemAllocInfoPackageVo vo) {
		if(vo.getCreateTime() != null) {
			vo.setCreateTimeStr(DateUtils.formatDBTime(vo.getCreateTime()));
		}
		if(vo.getFinishTime() != null) {
			vo.setFinishTimeStr(DateUtils.formatDBTime(vo.getFinishTime()));
		}
		
		boolean isFinish = AllocInfoPackageStatus.FINISH.getValue().equalsIgnoreCase(vo.getDataStatus());
		if(!isFinish)return;
		
		vo.setLogisticsCatLabel(LogisticsCats.getLabel(vo.getLogisticsCat()));
		
		if(vo.getOutLogicsticsPayCat() !=null) {
			vo.setOutLogicsticsPayCatLabel(PartnerLogisticsPayCats.getLabel(vo.getOutLogicsticsPayCat()));
		}
		
	}

	@Override
	public void convertLabel(List<UserOrderItemAllocInfoPackageVo> voList) {
		for(UserOrderItemAllocInfoPackageVo vo : voList) {
			this.convertLabel(vo);
		}
	}

}
