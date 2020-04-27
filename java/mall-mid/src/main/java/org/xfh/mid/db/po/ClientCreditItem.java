package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信用付款/偿还记录
 * 
 * @author cys
 *
 */
@ApiModel(value="信用付款/偿还记录", description="信用付款/偿还记录")
public class ClientCreditItem{

	@ApiModelProperty(value="id")
	protected Long id;
	
	@ApiModelProperty(value="客户id")
	protected Long clientId;							// 前台用户id	

	@ApiModelProperty(value="业务流水号,唯一")
	protected String sn;				// 业务流水号, 唯一. 

	@ApiModelProperty(value="订单业务id")	
	protected String orderBizId;		// 订单id

	@ApiModelProperty(value="金额")	
	protected BigDecimal fee;			// 金额	

	@ApiModelProperty(value="信用记录类型, 见 CreditItemCats")	
	protected String itemCat;			// 信用记录类型, 见 CreditItemCats

	@ApiModelProperty(value="后台用户id(如果是他操作)")	
	protected Long backUserId;			// 后台用户id(如果是他操作)

	@ApiModelProperty(value="数据创建时间")	
	protected Date createTime;

	@ApiModelProperty(value="如果这条记录的类型是偿还记录, 记录父item的流水号")
	protected String parentSn;			

	@ApiModelProperty(value="如果这条记录的类型是偿还记录, 付款类型")
	protected String repayCat;			

	@ApiModelProperty(value="如果item是借款状态, 用这个来标志是否偿还. true表示已经还钱")	
	protected String repayFlag;		// 如果item是借款状态, 用这个来标志是否偿还. Y表示已经还钱, N表示还没有. 

	@ApiModelProperty(value="偿还时间")	
	protected Date repayTime;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getParentSn() {
		return parentSn;
	}

	public void setParentSn(String parentSn) {
		this.parentSn = parentSn;
	}

	public String getOrderBizId() {
		return orderBizId;
	}

	public void setOrderBizId(String orderBizId) {
		this.orderBizId = orderBizId;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getItemCat() {
		return itemCat;
	}

	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}

	public String getRepayFlag() {
		return repayFlag;
	}

	public void setRepayFlag(String repayFlag) {
		this.repayFlag = repayFlag;
	}

	public Long getBackUserId() {
		return backUserId;
	}

	public void setBackUserId(Long backUserId) {
		this.backUserId = backUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getRepayCat() {
		return repayCat;
	}

	public void setRepayCat(String repayCat) {
		this.repayCat = repayCat;
	}

	public Date getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
}
