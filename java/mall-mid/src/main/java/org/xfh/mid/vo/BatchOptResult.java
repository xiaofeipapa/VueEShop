package org.xfh.mid.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量操作结果. 
 * @author cys
 *
 */
public class BatchOptResult {
	String bizId;
	List<Long> ids = new ArrayList<>();			// 所涉及的id
	List<BizError> errors = new ArrayList<>();
	
	public void addError(Long id, String msg) {
		errors.add(new BizError(id, msg));
	}
	
	public boolean isSucc() {
		return errors == null || errors.size() < 1;				
	}
	
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public void addId(Long id) {
		this.ids.add(id);
	}
	public List<BizError> getErrors() {
		return errors;
	}
	public void setErrors(List<BizError> errors) {
		this.errors = errors;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
}
