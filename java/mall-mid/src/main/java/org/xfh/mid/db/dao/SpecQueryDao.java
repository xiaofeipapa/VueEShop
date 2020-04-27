package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.mid.vo.ModalSpecGroupVo;

/**
 * 模型/商品的规格属性查询dao
 * 
 * @author cys
 *
 */
public interface SpecQueryDao {	

	// 根据商品模型, 获取模型下的所有规格属性组名和值. 
	List<ModalSpecGroupVo> getSpecGroupsWithChildren(@Param("modalId")Long modalId);
}
