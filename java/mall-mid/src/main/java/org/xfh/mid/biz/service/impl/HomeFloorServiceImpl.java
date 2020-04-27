package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableOrderService;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IHomeFloorService;
import org.xfh.mid.db.dao.HomeFloorDao;
import org.xfh.mid.db.dao.ProductQueryDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.HomeFloor;
import org.xfh.mid.db.po.HomeFloorProduct;
import org.xfh.mid.vo.HomeFloorVo;
import org.xfh.mid.vo.ProductVo;

@Component
public class HomeFloorServiceImpl extends AbstractSingleTableOrderService<HomeFloor> 
	implements IHomeFloorService{
	
	private Logger logger = LoggerFactory.getLogger(HomeFloorServiceImpl.class);
	
	@Autowired
	HomeFloorDao floorDao;
	
	@Autowired
	ProductQueryDao productQueryDao;

	@Override
	protected void checkBeforeCreateOrUpdate(HomeFloor entity, boolean isCreate) throws LogicException {
		
		VUtils.checkMandMax(entity.getName(), "标题", 40);
		super.checkDuplicateByField(DBs.NAME, entity.getName(), "标题已经重复", entity.getId(), isCreate);
		
		if(isCreate) {
			entity.setShowFront(false);

			// 设置排序						
			int maxOrder = commonDao.getMaxBySql(this.tableName, "order", null);
			entity.setOrder(maxOrder + 1);
		}
	}
	
	
	@Override
	protected String sameParentFilter(HomeFloor entity) {
		return "";
	}

	@Override
	public void toggleShow(Long userId, Long id) throws LogicException{
		
		HomeFloor data = daoHelper.getById(HomeFloor.class, id);
		
		boolean toSet = ! data.isShowFront();
		
		if(toSet) {
			// 检查是否有商品			
			String sql = DSqlUtils.andEqNumber(DBs.FLOOR_ID, id, false);
			int count = daoHelper.getCountBySql(HomeFloorProduct.class, sql);
			if(count < 1) {
				throw new LogicException("楼层需要有商品才能显示在网站前台");					
			}
			
		}
		
		HomeFloor param = new HomeFloor();
		param.setId(data.getId());
		param.setShowFront(toSet);
		daoHelper.updateById(HomeFloor.class, param);
		
	}


	@Override
	public List<HomeFloorVo> getAllHomeFloor() {
		return floorDao.getAllHomeFloor();
	}


	@Override
	public void checkAddProduct(Long userId, Long floorId, Long productId) throws LogicException {
		String sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		sql += DSqlUtils.andEqNumber(DBs.FLOOR_ID, floorId, false);
		
		int count = daoHelper.getCountBySql(HomeFloorProduct.class, sql);
		if(count > 0) {
			throw new LogicException("此商品已经存在");			
		}
		
		// check max
		int maxCount = 10;
		sql = DSqlUtils.andEqNumber(DBs.FLOOR_ID, floorId, false);
		count = daoHelper.getCountBySql(HomeFloorProduct.class, sql);
		if(count >= maxCount) {
			throw new LogicException("每个楼层最多能加" + maxCount + "个商品");					
		}

		// 设置排序
		String orderSql = DSqlUtils.andEqNumber(DBs.FLOOR_ID, floorId, true);					
		int maxOrder = commonDao.getMaxBySql(DBs.TABLE_HOME_FLOOR_PRODUCT, "order", orderSql);
				
		HomeFloorProduct prod = new HomeFloorProduct();
		prod.setFloorId(floorId);
		prod.setProductId(productId);
		prod.setOrder(maxOrder + 1);
		daoHelper.insertOne(HomeFloorProduct.class, prod);
	}


	@Override
	public List<ProductVo> getFloorProducts(Long userId, Long floorId) throws LogicException {
		List<Long> productIds = floorDao.getProductIdsByFloor(floorId);
		
		if(CheckUtils.isEmptyCollection(productIds)) {
			return null;
		}
		
		String sql = DSqlUtils.andInValues(DBs.ID, productIds);
		
		return productQueryDao.getProductVoBySql(sql);
	}

	@Transactional
	@Override
	public List<ProductVo> deleteFloorProduct(Long userId, Long floorId, Long productId) throws LogicException {

		String sql = DSqlUtils.andEqNumber(DBs.PRODUCT_ID, productId, false);
		sql += DSqlUtils.andEqNumber(DBs.FLOOR_ID, floorId, false);
		
		HomeFloorProduct prod = daoHelper.getOneBySql(HomeFloorProduct.class, sql);
		
		int order = prod.getOrder();
		
		// 删除这条记录
		daoHelper.deleteBySql(HomeFloorProduct.class, sql);
		
		// 同一楼层比这条记录order大的记录, 全部-1
		sql = DSqlUtils.andEqNumber(DBs.FLOOR_ID, floorId, false);
		sql += DSqlUtils.andGtNumber(DBs.ORDER, order, true);
		
		
		daoHelper.subtractFieldBySql(HomeFloorProduct.class, DBs.ORDER, 1, sql);
		
		return this.getFloorProducts(userId, floorId);		
	}


}
