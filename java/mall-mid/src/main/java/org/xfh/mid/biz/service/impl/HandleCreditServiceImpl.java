package org.xfh.mid.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.BigDecimalUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IBackLogService;
import org.xfh.mid.biz.service.IHandleCreditService;
import org.xfh.mid.db.dao.ClientBuyerDao;
import org.xfh.mid.db.dao.FinanceDao;
import org.xfh.mid.db.po.ClientCreditItem;
import org.xfh.mid.db.po.CreditOfflineRepayInfo;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.FinanceIncome;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.ClientCreditItemVo;
import org.xfh.mid.vo.FinanceIncomeVo;
import org.xfh.mid.vo.index.ClientIndexSearchForm;

@Component
public class HandleCreditServiceImpl extends AbstractSingleTableService<ClientCreditItem> 
	implements IHandleCreditService{
	
	private Logger logger = LoggerFactory.getLogger(HandleCreditServiceImpl.class);
	
	@Autowired
	IBackLogService backLogService;
	
	@Autowired
	ClientBuyerDao creditDao;
	
	@Autowired
	FinanceDao financeDao;

	@Override
	protected void checkBeforeCreateOrUpdate(ClientCreditItem entity, boolean isCreate) throws LogicException {
		// 此方法不需要
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public void checkAndPay(Long userId, String creditIds, String incomeIds) throws Exception{

		VUtils.checkM(creditIds, "请选择左侧的欠款项");
		VUtils.checkM(incomeIds, "请选择右侧的进账项");

		// 检查是否同一个client
		String sql = DSqlUtils.andFieldInSepStrings(DBs.ID, creditIds);
		List<ClientCreditItem> itemList = daoHelper.getListBySql(ClientCreditItem.class, sql);		
		BigDecimal credit = this.checkAllSameCreditUser(itemList);
		
		// 检查流水是否同一个寄款人
		sql = DSqlUtils.andFieldInSepStrings(DBs.ID, incomeIds);
		List<FinanceIncome> incomeList = daoHelper.getListBySql(FinanceIncome.class, sql);
		BigDecimal incomeAmount = this.checkAllSameIncomeUser(incomeList);
		
		if( credit.compareTo(incomeAmount) != 0) {
			String msg = "欠款总额%s不等于进账总额%s, 请重新选择";
			msg = String.format(BigDecimalUtils.toFixed2(credit), BigDecimalUtils.toFixed2(incomeAmount));
			throw new LogicException(msg);			
		}
		
		Date now = DateUtils.getChinaDate();
		
		// 设置欠款为已还款状态
		this.updateRepayStatus(itemList, userId, now);
		
		// 设置流水为已使用状态
		this.updateIncomeUseStatus(incomeList, userId, now);
		
		// 设置已支付关联
		CreditOfflineRepayInfo info = new CreditOfflineRepayInfo();
		info.setCreateTime(now);
		info.setCreateUserId(userId);
		info.setHandleAmount(incomeAmount);
		info.setCreditIds(creditIds);
		info.setIncomeIds(incomeIds);
		daoHelper.insertOne(CreditOfflineRepayInfo.class, info);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	private void updateIncomeUseStatus(List<FinanceIncome> incomeList, Long userId, Date now) throws LogicException {

		for(FinanceIncome income : incomeList) {
			
			FinanceIncome update = new FinanceIncome();
			
			update.setId(income.getId());
			update.setUseFlag("Y");
			update.setUseTime(now);
			update.setUseUserId(userId);
			
			daoHelper.updateById(FinanceIncome.class, update);
		}

	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	private void updateRepayStatus(List<ClientCreditItem> itemList, Long userId, Date now) {
		
		for(ClientCreditItem item : itemList) {
						
			ClientCreditItem update = new ClientCreditItem();
			update.setId(item.getId());
			update.setBackUserId(userId);
			update.setRepayFlag("Y");
			update.setRepayTime(now);
			update.setRepayCat(PayCats.OFFLINE.getValue());
			
			daoHelper.updateById(ClientCreditItem.class, update);			
		}
		
	}
	
	private BigDecimal checkAllSameCreditUser(List<ClientCreditItem> itemList) throws LogicException {

		BigDecimal credit = new BigDecimal(0);
		Long checkId = null;
		for(ClientCreditItem item : itemList) {
			
			if(checkId == null) {
				checkId = item.getClientId();
			}
			else {
				if(checkId.longValue() != item.getClientId().longValue()) {
					throw new LogicException("所选的欠款项必须都归属于同一个客户");							
				}
			}
			
			credit = credit.add(item.getFee());
						
		}
		return credit;
	}
	private BigDecimal checkAllSameIncomeUser(List<FinanceIncome> incomeList) throws LogicException {

		BigDecimal incomeAmount = new BigDecimal(0);
		String checkUser = null;
		for(FinanceIncome income : incomeList) {
			
			if(checkUser == null) {
				checkUser = income.getFromUserName();
			}else {
				if( ! checkUser.equalsIgnoreCase(income.getFromUserName())) {
					throw new LogicException("所选的进账必须都归属于同一个寄款人");					
				}
			}	
			incomeAmount = incomeAmount.add(income.getAmount());
		}
		return incomeAmount;
	}

	@Override
	public PageHolder<FinanceIncomeVo> getNoUseIncomePage(Long userId, IndexSearchForm form) {
		
		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		String moreSql = DSqlUtils.andEqString(DBs.USE_FLAG, "N", false);
		filter.setMoreSql(moreSql);
		
		int totalCount = financeDao.getFinanceIncomeVoIndexCount(filter);
		List<FinanceIncomeVo> dataList = financeDao.getFinanceIndexPageList(filter);
		
		return daoHelper.makePage(FinanceIncomeVo.class, totalCount, dataList, filter);
	}

	@Override
	public PageHolder<ClientBuyerVo> getDebitClientPage(Long userId, ClientIndexSearchForm form) throws Exception {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		filter.setLikeKeys(new String[]{"name", "user", "userPhone"});
		
		int totalCount = creditDao.getDebitClientCount(filter);
		List<ClientBuyerVo> dataList = creditDao.getDebitClientPageList(filter);
		
		return daoHelper.makePage(ClientBuyerVo.class, totalCount, dataList, filter);
	}

	@Override
	public List<ClientCreditItemVo> getDebtItemList(Long userId, Long clientId) {
		return creditDao.getDebtItemList(clientId);
	}
	
}
