package org.xfh.mid.db.po;

/**
 * 合作物流. 
 * @author cys
 *
 */
public class PartnerLogistics {

	protected Long id;
	protected String name;		// 车牌号
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
}
