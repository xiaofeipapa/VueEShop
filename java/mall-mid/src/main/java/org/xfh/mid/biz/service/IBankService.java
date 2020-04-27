package org.xfh.mid.biz.service;

import java.util.List;

import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchForm;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.index.Level2Vo;
import org.xfh.mid.db.po.Bank;

public interface IBankService extends ISingleTableService<Bank>{

	// 返回父级银行
	List<Bank> getParentBankList(Long userId) throws Exception;

	// 返回次级银行
	List<Bank> getChildBankList(Long userId, Long parentId) throws Exception;

	// 返回次级银行
	PageHolder<Level2Vo> getBankPage(Long userId, IndexSearchForm form) throws Exception;
	
	// 删除数据
	void deleteBank(Long userId,Long id, int level)throws Exception;
}
