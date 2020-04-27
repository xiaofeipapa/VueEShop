package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.BizError;
import org.xfh.mid.vo.ExcelExportVo;
import org.xfh.mid.vo.SupplierOrderVo;
import org.xfh.mid.vo.form.MakeSupplierOrderForm;

public interface ISupplierOrderService{
	
	// 创建一批的计划采购订单
	List<BizError> createPlanOrder(Long userId, MakeSupplierOrderForm form)throws Exception;
	
	// 获取订单数据
	PageHolder<SupplierOrderVo> getSupplierOrderPage(Long userId, IndexSearchForm form) throws Exception;
	
	// 返回订单和商品, 预备作修改
	SupplierOrderVo getOrderForEdit(Long userId, Long orderId) throws Exception;
	
	// 更新某个订单
	void updateSupplierOrder(Long userId, SupplierOrderVo form)throws Exception;
	
	// 根据已有的供应商订单, 生成财务数据以供财务人员进行付款. 
	void confirmAll(Long userId, String ids);
	
	// 删除订单
	void deleteAll(Long userId, String ids) throws Exception ;
	
	// 导出excel
	ExcelExportVo exportIt(Long userId, Long orderId) throws Exception ;
	
	// 保存付款凭据,变成已付款状态
	void confirmPaid(Long userId, Long orderId, String imageUrl) throws Exception ;
	
	// 确认变成收货
	void confirmReceive(Long userId, Long orderId) throws Exception ;
	
}
