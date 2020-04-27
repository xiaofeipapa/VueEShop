package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.UserCheckActivityVo;
import org.xfh.mid.vo.UserCheckProductVo;
import org.xfh.mid.vo.WarehouseProductVo;

/**
 * 仓库盘点业务
 * 
 * @author cys
 *
 */
public interface CheckActivityDao {	
	
	// 返回盘点活动分页
	List<UserCheckActivityVo> getCheckActivityPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回盘点活动总数
	int getCheckActivityCount(@Param("searchFilter")IndexSearchFilter searchFilter);

	
	int getWarehouseProductCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回盘点商品所需要的商品选择控件数据(带仓库库存) 
	List<WarehouseProductVo> getWarehouseProductPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 返回盘点商品和关联的结果
	List<UserCheckProductVo> getCheckProducts(@Param("activityId")Long activityId);
	
	// 返回盘点商品的分页
	List<UserCheckProductVo> getCheckProductPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	int getCheckProductCount(@Param("searchFilter")IndexSearchFilter searchFilter);

}
