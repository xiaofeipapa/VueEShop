package org.xfh.mid.db.po;

/**
 * 通用的参数表. 商品模型和商品实例都使用这个表, 通过字段区分是模型还是势力. 
 * @author cys
 *
 */
public class ProductParam {
	
	public static final String TABLE_NAME = "ProductParam";

	public static final String CAT_MODAL = "modal";
	public static final String CAT_PRODUCT = "product";

	protected Long id;
	protected String name;		// 参数名字
	protected String value;		// 参数的值
	protected String cat;		// 模型还是实例
	protected Long dataId;		// 模型表或实例表的id
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
}
