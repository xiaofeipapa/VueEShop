package org.xfh.mid.vo;

import org.xfh.mid.db.po.Warehouse;

public class WarehouseVo extends Warehouse{

	protected DistrictDataVo districtVo;

	public DistrictDataVo getDistrictVo() {
		return districtVo;
	}

	public void setDistrictVo(DistrictDataVo districtVo) {
		this.districtVo = districtVo;
	} 
}
