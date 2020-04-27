package org.xfh.mid.biz.service;

import java.util.Collection;

import org.xfh.mid.vo.ModalSpecGroupVo;

/**
 * 封装商品规格属性组, 属性值的查询. 
 * 
 * @author cys
 *
 */
public interface ISpecQueryService{

	// 获取模型关联的规格属性组/值
	Collection<ModalSpecGroupVo> getModalSpecGroupAndValues(Long loginUserId, Long modalId);
		
}
