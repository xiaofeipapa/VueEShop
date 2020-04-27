package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.UserCheckActivity;
import org.xfh.mid.db.po.UserCheckProduct;
import org.xfh.mid.vo.UserCheckActivityVo;
import org.xfh.mid.vo.UserCheckProductVo;
import org.xfh.mid.vo.WarehouseProductVo;
import org.xfh.mid.vo.form.CheckActivityAddProductForm;
import org.xfh.mid.vo.index.WarehouseActivityIndexSearchForm;
import org.xfh.mid.vo.index.WarehouseProductIndexSearchForm;

public interface ICheckActivityService  extends ISingleTableService<UserCheckActivity> {
	
	PageHolder<UserCheckActivityVo> getCheckActivityPage(Long userId, WarehouseActivityIndexSearchForm form);
	
	UserCheckActivityVo getDetailById(Long userId, Long id, boolean withProducts);

	// 增加盘点商品
	void addCheckProducts(Long userId, CheckActivityAddProductForm form);

	// 返回盘点商品所需要的商品选择数据(带仓库库存) 
	PageHolder<WarehouseProductVo> getWarehouseProductPage(Long userId, WarehouseProductIndexSearchForm form)throws Exception;

	// 删除盘点商品,返回剩余的商品记录
	void deleteProduct(Long userId, Long id, String level);
	
	List<UserCheckProductVo>  getCheckProducts(Long userId, Long activityId);
	
	// 增加检查结果
	Long addCheckResult(Long userId, UserCheckProduct form) throws Exception;
	
	// 返回盘点活动的商品分页
	PageHolder<UserCheckProductVo>  getCheckProductPage(Long userId, WarehouseProductIndexSearchForm form);
	
	void deleteActivityAndProducts(Long userId, Long activityId)throws Exception;
}
