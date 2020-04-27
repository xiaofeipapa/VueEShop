package org.xfh.mid.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.index.Level2Vo;
import org.xfh.mid.vo.BankAccountVo;

/**
 * 银行账户和银行信息 dao
 * 
 * @author cys
 *
 */
public interface BankInfoDao {	

	List<Level2Vo> getBankPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	int getBankCount(@Param("searchFilter")IndexSearchFilter searchFilter);

	List<BankAccountVo> getBankAccountPageList(@Param("searchFilter")IndexSearchFilter searchFilter);
	
	int getBankAccountCount(@Param("searchFilter")IndexSearchFilter searchFilter);
	
}
