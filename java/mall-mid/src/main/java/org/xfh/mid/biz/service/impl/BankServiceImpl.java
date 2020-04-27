package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.index.Level2Vo;
import org.xfh.mid.biz.service.IBankService;
import org.xfh.mid.db.dao.BankInfoDao;
import org.xfh.mid.db.po.Bank;
import org.xfh.mid.db.po.DBs;

@Component
public class BankServiceImpl extends AbstractSingleTableService<Bank> 
	implements IBankService{
	
	private Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
	
	@Autowired
	BankInfoDao level2Dao;

	@Override
	public List<Bank> getParentBankList(Long userId) throws Exception {
		String sql = DSqlUtils.andEqNull(DBs.PARENT_ID, false);
		return daoHelper.getListBySql(Bank.class, sql);
	}

	@Override
	protected void checkBeforeCreateOrUpdate(Bank entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getLevel(), "请选择层级");
		VUtils.checkMandMax(entity.getName(), "名称", 40);
		
		if(entity.getLevel() == Bank.CHILD && entity.getParentId() == null) {
			throw new LogicException("请选择银行数据");
		}
		
		String sql = DSqlUtils.andEqNull(DBs.PARENT_ID, false);
		if(entity.getLevel() == Bank.CHILD) {
			sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, entity.getParentId(), false);
		}
		sql += DSqlUtils.andEqString(DBs.NAME, entity.getName(), false);
		
		super.checkDuplicateBySql(sql, "重复的名称: " + entity.getName(), entity.getId(), isCreate);
	}

	@Override
	public List<Bank> getChildBankList(Long userId, Long parentId) throws Exception {

		String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, parentId, false);
		return daoHelper.getListBySql(Bank.class, sql);
	}

	@Override
	public PageHolder<Level2Vo> getBankPage(Long userId, IndexSearchForm form) throws Exception {
		
		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		// 只需要清理自己的数据
		String moreSql = DSqlUtils.andEqNumber("a." + DBs.CREATE_USER_ID, userId, false);
		filter.setMoreSql(moreSql);
		
		int totalCount = level2Dao.getBankCount(filter);
		List<Level2Vo> dataList = level2Dao.getBankPageList(filter);
		
		PageHolder<Level2Vo> holder = new PageHolder<Level2Vo>();
		holder.setPageSize(filter.getPageSize());
		holder.setPageNo(filter.getPageNo());
		holder.setDataList(dataList);
		holder.setTotalCount(totalCount);
		
		// 计算分页数量
		holder.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
		
		return holder;
		
	}

	@Transactional
	@Override
	public void deleteBank(Long userId, Long id, int level) throws Exception {
		
		if(level == Bank.PARENT) {
			super.deleteById(userId, id);
			return;
		}
		
		// 删除本数据和关联子数据
		String sql = DSqlUtils.andEqNumber(DBs.PARENT_ID, id, false);
		daoHelper.deleteById(Bank.class, id);
		daoHelper.deleteBySql(Bank.class, sql);
	}

//	@Override
//	public Long addNewData(Long userId, String name) throws Exception {
//		
//		Bank bank = new Bank();
//		bank.setCreateTime(DateUtils.getChinaDate());
//		bank.setCreateUserId(userId);
//		bank.setLevel(Bank.PARENT);
//		bank.setName(name);
//		return daoHelper.insertOne(Bank.class, bank);		
//	}
	
}
