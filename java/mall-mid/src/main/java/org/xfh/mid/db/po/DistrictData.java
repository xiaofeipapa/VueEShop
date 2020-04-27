package org.xfh.mid.db.po;

/**
 * 区域信息
 * @author cys
 *
 */
public class DistrictData {

	public static final int PROVINCE = 1;		// 省份或直辖市
	public static final int CITY = 2;			// 城市
	public static final int DISTRICT = 3;		// 城市下的区域. 如深圳市的福田区, 南山区

	protected Long id;				// 数据库id
	protected String code;				// 国家统计数据的code, 如 110000 (表示北京市) 
	protected String name;				// 中文名字, 如北京市
	protected int level = 1;			// 1 省/直辖市  2 市/直辖市下的辖区  3 县区
	protected String parentCode;		// 父区域的code

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
