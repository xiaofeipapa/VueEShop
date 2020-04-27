package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.ClientBuyerVo;
import org.xfh.mid.vo.ClientCreditItemVo;

/**
 * 客户dao, 包括欠款,还款
 * 
 * @author cys
 *
 */
public interface ClientBuyerDao {	
	
	// 包括欠款等信息. 用户可能欠款也可能不欠款. 
	ClientBuyerVo getClientFullDetail(Long clientId);
	
	// 返回带欠款的客户信息, 
	ClientBuyerVo getDebitClient(Long clientId);
	// 应收分页查询
	List<ClientBuyerVo> getDebitClientPageList(@Param("searchFilter")IndexSearchFilter searchFilter);	
	int getDebitClientCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	// 获取客户的欠款列表
	List<ClientCreditItemVo> getDebtItemList(@Param("clientId")Long clientId);
		
}
