package org.xfh.mid.biz.helper;

import java.util.List;

import org.xfh.mid.vo.UserOrderItemAllocInfoVo;

/**
 * 出库单 / 调拨 辅助类
 * 
 * @author cys
 *
 */
public interface IPackageNoteHelper {

	// 检索出有多少个仓库id
	List<Long> filterWarehouseIds(List<UserOrderItemAllocInfoVo> allocList);
	
	// 检索出有多少个供应商id
	List<Long> filterSupplierIds(List<UserOrderItemAllocInfoVo> allocList);
}
