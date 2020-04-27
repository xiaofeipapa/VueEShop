package org.xfh.mid.vo.form;

import java.util.List;

import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;

public class TempAllocInfoPackageVo {

	List<UserOrderItemAllocInfoPackageVo> noteList ; 						// 出库单带关联信息
	List<UserOrderItemAllocInfoVo> supplierOrders ; 	// 供应商采购项
	public List<UserOrderItemAllocInfoPackageVo> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<UserOrderItemAllocInfoPackageVo> noteList) {
		this.noteList = noteList;
	}
	public List<UserOrderItemAllocInfoVo> getSupplierOrders() {
		return supplierOrders;
	}
	public void setSupplierOrders(List<UserOrderItemAllocInfoVo> supplierOrders) {
		this.supplierOrders = supplierOrders;
	}

}
