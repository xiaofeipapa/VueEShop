package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IBankAccountService;
import org.xfh.mid.db.dao.BankInfoDao;
import org.xfh.mid.db.po.BankAccount;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.vo.BankAccountVo;
import org.xfh.mid.vo.index.BankAccountIndexSearchForm;

@Component
public class BankAccountServiceImpl extends AbstractSingleTableService<BankAccount> 
	implements IBankAccountService{
	
	private Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
	
	@Autowired
	BankInfoDao bankDao;

	@Override
	protected void checkBeforeCreateOrUpdate(BankAccount entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getUserName(), "请输入开户名称");
		VUtils.checkM(entity.getAccount(), "请输入账户");
		VUtils.checkM(entity.getBankId(), "请选择开户银行");
		VUtils.checkM(entity.getBranchId(), "请选择分行");
		
		super.checkDuplicateByField(DBs.ACCOUNT, entity.getAccount(), "开户账号重复: " + entity.getAccount(), entity.getId(), isCreate);
	}

	@Override
	public PageHolder<BankAccountVo> getBankAccountPage(Long userId, BankAccountIndexSearchForm form) throws Exception {

		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		filter.setLikeKeys(new String[]{"userName", "account"});
		
		String moreSql = DSqlUtils.andEqString(DBs.CAT, form.getCat(), true);
		filter.setMoreSql(moreSql);
				
		int totalCount = bankDao.getBankAccountCount(filter);
		List<BankAccountVo> dataList = bankDao.getBankAccountPageList(filter);
		
		PageHolder<BankAccountVo> holder = new PageHolder<>();
		holder.setPageSize(filter.getPageSize());
		holder.setPageNo(filter.getPageNo());
		holder.setDataList(dataList);
		holder.setTotalCount(totalCount);
		
		// 计算分页数量
		holder.setPageCount(this.calculatePageCount(totalCount, filter.getPageSize()));
		
		return holder;
	}
	

	
}
