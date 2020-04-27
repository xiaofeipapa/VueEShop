package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IFinanceIncomeService;
import org.xfh.mid.db.dao.FinanceDao;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.FinanceIncome;
import org.xfh.mid.vo.FinanceIncomeVo;
import org.xfh.mid.vo.index.FinanceIndexSearchForm;

@Component
public class FinanceIncomeServiceImpl extends AbstractSingleTableService<FinanceIncome> 
	implements IFinanceIncomeService{
	
	private Logger logger = LoggerFactory.getLogger(FinanceIncomeServiceImpl.class);
	
	@Autowired
	FinanceDao financeDao;
	
	@Override
	protected void checkBeforeCreateOrUpdate(FinanceIncome entity, boolean isCreate) throws LogicException {

		VUtils.checkBigDecimalMustGtZero(entity.getAmount(), "金额必须大于0");
		VUtils.checkMax(entity.getBankSeq(), 40, "银行单据");
		VUtils.checkM(entity.getFromTime(), "请选择寄款时间");

		VUtils.checkM(entity.getFromUserName(), "请选择寄款人");
		VUtils.checkM(entity.getFromAccount(), "请选择寄款账户");
		VUtils.checkM(entity.getFromBankId(), "请选择寄款银行");
		VUtils.checkM(entity.getFromBranchId(), "请选择寄款分行");

		VUtils.checkM(entity.getToUserName(), "请选择收款人");
		VUtils.checkM(entity.getToAccount(), "请选择收款账户");
		VUtils.checkM(entity.getToBankId(), "请选择收款银行");
		VUtils.checkM(entity.getToBranchId(), "请选择收款分行");

		VUtils.checkM(entity.getImageUrls(), "请上传单据图片");
		VUtils.checkMandMax(entity.getRemark(), "备注", 500);
		
		if(isCreate) {
			entity.setDataStatus(FinanceIncome.TEMP);
		}
				
	}

	@Override
	public PageHolder<FinanceIncomeVo> getIncomePage(Long userId, FinanceIndexSearchForm form) throws Exception {
		
		IndexSearchFilter filter = IndexSearchFilter.fromIndexForm(form);
		
		filter.setLikeKeys(new String[] {"fromUserName", "fromAccount"});
		
		int totalCount = financeDao.getFinanceIncomeVoIndexCount(filter);
		List<FinanceIncomeVo> dataList = financeDao.getFinanceIndexPageList(filter);
		
		return daoHelper.makePage(FinanceIncomeVo.class, totalCount, dataList, filter);
	}

	@Override
	public FinanceIncomeVo getVoById(Long userId, Long id) throws Exception {
		return financeDao.getById(id);
	}

	@Override
	public void submitData(Long userId, Long id) throws Exception {
		daoHelper.updateFieldById(FinanceIncome.class, id, DBs.DATA_STATUS, FinanceIncome.Valid);
	}


}
