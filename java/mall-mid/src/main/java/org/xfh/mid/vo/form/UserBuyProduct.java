package org.xfh.mid.vo.form;

public class UserBuyProduct {

	protected Long id;			// productId
	protected int count;
	protected String modalTitle;
	protected String specValueString;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getModalTitle() {
		return modalTitle;
	}
	public void setModalTitle(String modalTitle) {
		this.modalTitle = modalTitle;
	}
	public String getSpecValueString() {
		return specValueString;
	}
	public void setSpecValueString(String specValueString) {
		this.specValueString = specValueString;
	}
	
}
