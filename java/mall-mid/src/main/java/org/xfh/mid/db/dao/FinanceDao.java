package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.mid.vo.FinanceIncomeVo;

/**
 * 财务流水查询
 * 
 * @author cys
 *
 */
public interface FinanceDao {	

	// 查询财务流水分页
	List<FinanceIncomeVo> getFinanceIndexPageList(@Param("searchFilter")IndexSearchFilter searchFilter);

	// 查询财务流水分页总数
	int getFinanceIncomeVoIndexCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	FinanceIncomeVo getById(@Param("id")Long id);
	
}
