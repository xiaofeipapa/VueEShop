package org.xfh.mid.vo.form;

import java.util.ArrayList;
import java.util.List;

public class AllocBatchSaveForm {

	Long itemId;
	List<AllocEachData> addList = new ArrayList<>();
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public List<AllocEachData> getAddList() {
		return addList;
	}
	public void setAddList(List<AllocEachData> addList) {
		this.addList = addList;
	}
}
