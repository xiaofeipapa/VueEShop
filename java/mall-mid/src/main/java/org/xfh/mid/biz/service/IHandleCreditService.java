package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.ClientCreditItemVo;
import org.xfh.mid.vo.FinanceIncomeVo;
import org.xfh.mid.vo.index.ClientIndexSearchForm;

/**
 * 关于 欠款 ClientCreditItem 的相关操作
 * 
 * @author cys
 *
 */
public interface IHandleCreditService {
	
	// 应收查询(欠款的客户分页) 
	PageHolder<ClientBuyerVo> getDebitClientPage(Long userId, ClientIndexSearchForm form) throws Exception;

	// 获取可以使用的流水
	PageHolder<FinanceIncomeVo> getNoUseIncomePage(Long userId,IndexSearchForm form);
	
	// 检查进账和欠款是否匹配, 并进行偿还
	void checkAndPay(Long userId, String creditIds, String incomeIds) throws Exception;

	// 获取用户的关联欠款(不需要分页)
	List<ClientCreditItemVo> getDebtItemList(Long userId,Long clientId);
	
}
