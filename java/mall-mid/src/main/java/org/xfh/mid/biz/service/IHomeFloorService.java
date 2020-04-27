package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.ISingleTableOrderService;
import org.xfh.mid.db.po.HomeFloor;
import org.xfh.mid.vo.HomeFloorVo;
import org.xfh.mid.vo.ProductVo;

public interface IHomeFloorService extends ISingleTableOrderService<HomeFloor>{
		
	List<HomeFloorVo> getAllHomeFloor();
	
	void toggleShow(Long userId, Long id)throws LogicException;
	
	void checkAddProduct(Long userId, Long floorId, Long productId) throws LogicException;
	
	List<ProductVo> getFloorProducts(Long userId, Long floorId) throws LogicException;
	
	List<ProductVo> deleteFloorProduct(Long userId, Long floorId, Long productId) throws LogicException;
}
