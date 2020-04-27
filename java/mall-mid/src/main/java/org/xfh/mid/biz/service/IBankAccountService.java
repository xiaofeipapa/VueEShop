package org.xfh.mid.biz.service;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.BankAccount;
import org.xfh.mid.vo.BankAccountVo;
import org.xfh.mid.vo.index.BankAccountIndexSearchForm;

public interface IBankAccountService extends ISingleTableService<BankAccount>{

	// 返回带关联的分页信息
	PageHolder<BankAccountVo> getBankAccountPage(Long userId, BankAccountIndexSearchForm form) throws Exception;
}
