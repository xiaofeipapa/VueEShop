package org.xfh.mid.vo;

/**
 * @author cys
 *
 */
public class IdNameVo {

	protected Long id;
	protected String name;
	
	public IdNameVo() {
		super();
	}
	public IdNameVo(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
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
