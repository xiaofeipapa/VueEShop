package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.UserCheckActivityVo;
import org.xfh.mid.vo.WarehousePosVo;

/**
 * 仓库/仓位单表无法满足的数据库业务. 
 * 
 * @author cys
 *
 */
public interface WarehouseQueryDao {	

	// 仓位分页数据
	List<WarehousePosVo> getWarehousePosPageList(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 返回仓位数据
//	WarehousePosVo getWarehousePosVoById(@Param("id")Long id);

}
