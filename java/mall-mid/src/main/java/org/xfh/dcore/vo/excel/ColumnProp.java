package org.xfh.dcore.vo.excel;

/**
 * 列单元格属性. 
 * @author cys
 *
 */
public class ColumnProp {
	String name;		// 例如供应商名称
	String field;		// 例如 supplierName
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public ColumnProp(String name, String field) {
		super();
		this.name = name;
		this.field = field;
	}
	
	
}
