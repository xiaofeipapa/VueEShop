package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.UserOrderItemAllocInfoPackageVo;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;

public interface UserOrderItemAllocInfoDao {

	// 根据订单项获取关联的调拨信息
	List<UserOrderItemAllocInfoVo> getAllocInfoListByItemId(@Param("itemId")Long itemId);

	// 根据订单关联的调拨信息
	List<UserOrderItemAllocInfoVo> getAllocInfoListOfOrder(@Param("orderBizId")String orderBizId, @Param("allocCat")String allocCat);

	// 根据出库单获取关联的仓库调拨信息
	List<UserOrderItemAllocInfoVo> getAllocInfoListOfPackage(@Param("packageSn")String packageSn);
	
	// 找出订单关联的发货单列表
	List<UserOrderItemAllocInfoPackageVo> getPackageListOfOrder(@Param("sqlFilter")String sqlFilter);
	
	// 找出发货单(严格限定订单和仓库id)
	UserOrderItemAllocInfoPackageVo getPackageByOrderAndWarehouse(@Param("orderBizId")String orderBizId
			, @Param("warehouseId")Long warehouseId);
		
	// 删除临时的出库单信息
	void deleteTempNote(@Param("bizId")String bizId);
	
	// 更新订单关联的出库单信息
	void updateNoteStatusBySql(@Param("sqlFilter")String sqlFilter, @Param("dataStatus")String dataStatus);
	
	// 出货单总数
	int getPackageCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 出货单分页列表
	List<UserOrderItemAllocInfoPackageVo> getPackageIndexPageList(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 根据出库单获取关联的仓库调拨信息(同商品数量合并) 
	List<UserOrderItemAllocInfoVo> getNeedPackageItems(@Param("packageSn")String packageSn);
}
