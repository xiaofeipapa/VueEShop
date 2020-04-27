package org.xfh.mid.db.po;

/**
 * 仓位. 每个仓库有多个仓位 
 * @author cys
 *
 */
public class WarehousePos {

	protected Long id;						// 数据库id
	protected String code;					// 仓位自定义code, 必须唯一
	protected String remark;				// 中文备注
	protected Long warehouseId;				// 仓库id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}	

}
