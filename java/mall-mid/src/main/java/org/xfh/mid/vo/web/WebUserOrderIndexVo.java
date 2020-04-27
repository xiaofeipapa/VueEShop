package org.xfh.mid.vo.web;

import java.util.List;

import org.xfh.mid.vo.UserOrderVo;

/**
 * 用户中心 / 我的订单
 * @author cys
 *
 */
public class WebUserOrderIndexVo {

	List<UserOrderVo> dataList;
	long pageCount;
	int waitPaidNum;			// 等候付款的数目
	int waitAcceptNum;			// 等候签收的数目
	public List<UserOrderVo> getDataList() {
		return dataList;
	}
	public void setDataList(List<UserOrderVo> dataList) {
		this.dataList = dataList;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public int getWaitPaidNum() {
		return waitPaidNum;
	}
	public void setWaitPaidNum(int waitPaidNum) {
		this.waitPaidNum = waitPaidNum;
	}
	public int getWaitAcceptNum() {
		return waitAcceptNum;
	}
	public void setWaitAcceptNum(int waitAcceptNum) {
		this.waitAcceptNum = waitAcceptNum;
	}

}
