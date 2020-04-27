package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.DistrictDataVo;

/**
 * 封装商品分类特殊的查询操作
 * 
 * @author cys
 *
 */
public interface DistrictDao {	

	// 返回孙子区域列表
	List<DistrictDataVo> getDistrictPageListForSearch(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 返回孙子区域
	DistrictDataVo getDistrictVoByCode(@Param("code")String code);

}
