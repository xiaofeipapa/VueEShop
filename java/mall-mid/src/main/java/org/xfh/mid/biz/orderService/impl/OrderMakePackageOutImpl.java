package org.xfh.mid.biz.orderService.impl;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.RefUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.orderService.IOrderMakePackageOut;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.UserOrderItemAllocInfoPackage;
import org.xfh.mid.enums.AllocInfoPackageStatus;
import org.xfh.mid.enums.LogisticsCats;
import org.xfh.mid.enums.PartnerLogisticsPayCats;
import org.xfh.mid.enums.UserOrderStatus;
import org.xfh.mid.vo.form.ProductOutFinishPackageForm;


@Component
public class OrderMakePackageOutImpl extends AbstractOrderHandler implements IOrderMakePackageOut{
	
	private Logger logger = LoggerFactory.getLogger(OrderMakePackageOutImpl.class);
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void sendPackage(Long backUserId, ProductOutFinishPackageForm form) throws Exception {
		
		// 检查数据
		this.checkData(form);
		
		Date now = DateUtils.getChinaDate();
		// 更新出库单信息
		form.setFinishUserId(backUserId);
		form.setFinishTime(now);
		form.setDataStatus(AllocInfoPackageStatus.SENT.getValue());		
		TreeMap<String, Object> param = RefUtils.copyFieldsToMap(form, false, null, null);
		commonDao.updateById(DBs.TABLE_USER_ORDER_ITEM_ALLOC_INFO_PACKAGE, param, form.getId());
		
		// 检查该订单有多少个出库单
		UserOrderItemAllocInfoPackage vo = daoHelper.getById(UserOrderItemAllocInfoPackage.class, form.getId());
		String sql = DSqlUtils.andEqString(DBs.ORDER_BIZ_ID, vo.getOrderBizId(), false);
		List<UserOrderItemAllocInfoPackage> voList = daoHelper.getListBySql(UserOrderItemAllocInfoPackage.class, sql);
		
		boolean allFinish = super.allPackageMatch(form.getId(), AllocInfoPackageStatus.SENT);
		
		// 更新订单状态
		if(allFinish) {
			// 更新为已发货
			super.updateOrderStatus(UserOrderStatus.SEND_CAR.getValue(), vo.getOrderBizId());			
		}
	}

	
	private void checkData(ProductOutFinishPackageForm form) throws Exception {
		
		VUtils.checkM(form.getLogisticsCat(), "请选择物流类型");
		
		boolean isOwn = LogisticsCats.OWN.getValue().equalsIgnoreCase(form.getLogisticsCat());
		
		if(isOwn) {
			VUtils.checkM(form.getCarId(), "请选择发车信息");
			VUtils.checkM(form.getCarDriverId(), "请选择司机信息");
			VUtils.checkM(form.getOutLogicsticsImage(), "请拍照上传拣货凭证");
		}
		else {
			VUtils.checkM(form.getOutLogicsticsId(), "请选择合作物流");
			VUtils.checkM(form.getOutLogicsticsNo(), "请输入快递单号");
			VUtils.checkM(form.getOutLogicsticsPayCat(), "请选择费用支付类型");

			boolean isCash = PartnerLogisticsPayCats.CASH.getValue().equalsIgnoreCase(form.getOutLogicsticsPayCat());
			if(isCash) {
				VUtils.checkM(form.getOutLogicsticsFee(), "请输入物流费用");
				
			}
			VUtils.checkM(form.getOutLogicsticsImage(), "请拍照上传物流单据");
			
		}
		
	}


}
