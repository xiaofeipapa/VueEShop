package org.xfh.web.vo;

import java.util.List;

public class ModalSpecMapVo {
	Long modalId;
	List<Long> specIds;
	public Long getModalId() {
		return modalId;
	}
	public void setModalId(Long modalId) {
		this.modalId = modalId;
	}
	public List<Long> getSpecIds() {
		return specIds;
	}
	public void setSpecIds(List<Long> specIds) {
		this.specIds = specIds;
	}
	
}
