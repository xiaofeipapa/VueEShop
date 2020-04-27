package org.xfh.mid.biz.service;

import java.util.Collection;
import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.db.po.DistrictData;
import org.xfh.mid.vo.DistrictDataVo;
import org.xfh.mid.vo.DistrictWithParentVo;

public interface IDistrictDataService  extends ISingleTableService<DistrictData>{
	
	// 带层次结构的树形区域信息
	Collection<DistrictDataVo> getAllDistrictData();
	
	// 用于控件的区域信息方法
	List<DistrictDataVo> getDistrictPageListForSearch(IndexSearchFilter searchFilter);
	
	DistrictDataVo getDistrictVoByCode(String code);
	
	// 回显区域信息的方法
	DistrictWithParentVo getAllParentInfoByChildCode(String districtCode) throws Exception;
	
}
