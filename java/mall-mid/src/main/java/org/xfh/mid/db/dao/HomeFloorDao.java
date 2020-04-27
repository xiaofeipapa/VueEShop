package org.xfh.mid.db.dao;

import java.util.List;

import org.xfh.mid.vo.HomeFloorVo;

/**
 * 前台首页相关dao
 * 
 * @author cys
 *
 */
public interface HomeFloorDao {

	List<HomeFloorVo> getAllHomeFloor();
	
	List<Long> getProductIdsByFloor(Long floorId);
		
}
