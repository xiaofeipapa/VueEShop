package org.xfh.mid.vo;

import org.xfh.mid.db.po.Warehouse;
import org.xfh.mid.db.po.WarehousePos;

public class WarehousePosVo extends WarehousePos{

	protected String warehouseName;
	protected Warehouse warehouse;

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

}
