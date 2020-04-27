package org.xfh.mid.db.po;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FinanceIncome {

	public static final String TEMP = "temp";			// 临时数据
	public static final String Valid = "valid";			// 正式数据

	protected Long id;
	protected BigDecimal amount;			//金额
    protected String bankSeq;  				// 银行单据流水号
    
    protected String fromUserName;			// 发起人
    protected Integer fromBankId;   		// 发起银行
    protected Integer fromBranchId;			// 发起银行支行
    protected String fromAccount;			// 发起账号
    
    @DateTimeFormat(pattern = DBs.COMMON_TIME_FORMAT)
    @JsonFormat(pattern = DBs.COMMON_TIME_FORMAT,timezone="GMT+8")
    protected Date fromTime;;				// 发起时间

    protected String toUserName;			//收款人
    protected Integer toBankId;				//收款银行
    protected Integer toBranchId;			//收款银行支行
    protected String toAccount;				//收款账号
    
    @DateTimeFormat(pattern = DBs.COMMON_TIME_FORMAT)
    @JsonFormat(pattern = DBs.COMMON_TIME_FORMAT,timezone="GMT+8")
    protected Date toTime;					//收款时间
    
    protected String imageUrls;				// 单据        
    protected String remark;				// 创建时候的remark, 保存之后, 会保存到该角色的remark字段. 

    protected Long createUserId;			// 创建人
    protected String dataStatus;			// 数据状态       
    
    @DateTimeFormat(pattern = DBs.COMMON_TIME_FORMAT)
    @JsonFormat(pattern = DBs.COMMON_TIME_FORMAT,timezone="GMT+8")
    protected Date createTime;				//录入记录时间 
    
    protected String useFlag;				// 该流水已被使用(用于偿还) , 默认为 N       
    protected Date useTime;					// 使用时间
	protected Long useUserId;				// 使用者
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getBankSeq() {
		return bankSeq;
	}
	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Integer getFromBankId() {
		return fromBankId;
	}
	public void setFromBankId(Integer fromBankId) {
		this.fromBankId = fromBankId;
	}
	public Integer getFromBranchId() {
		return fromBranchId;
	}
	public void setFromBranchId(Integer fromBranchId) {
		this.fromBranchId = fromBranchId;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public Integer getToBankId() {
		return toBankId;
	}
	public void setToBankId(Integer toBankId) {
		this.toBankId = toBankId;
	}
	public Integer getToBranchId() {
		return toBranchId;
	}
	public void setToBranchId(Integer toBranchId) {
		this.toBranchId = toBranchId;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
	public Long getUseUserId() {
		return useUserId;
	}
	public void setUseUserId(Long useUserId) {
		this.useUserId = useUserId;
	}
}
