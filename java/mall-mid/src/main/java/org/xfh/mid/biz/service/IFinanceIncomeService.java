package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.FinanceIncome;
import org.xfh.mid.vo.FinanceIncomeVo;
import org.xfh.mid.vo.index.FinanceIndexSearchForm;

public interface IFinanceIncomeService extends ISingleTableService<FinanceIncome>{

	// 获取流水分页
	PageHolder<FinanceIncomeVo> getIncomePage(Long userId, FinanceIndexSearchForm form) throws Exception;
	
	FinanceIncomeVo getVoById(Long userId, Long id)throws Exception;
	
	void submitData(Long userId, Long id)throws Exception;
}
