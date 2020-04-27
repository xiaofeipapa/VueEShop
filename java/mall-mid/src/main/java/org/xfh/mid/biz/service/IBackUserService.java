package org.xfh.mid.biz.service;

import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.ISingleTableService;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.db.po.BackUser;

public interface IBackUserService extends ISingleTableService<BackUser>{
	
	// 处理后台用户登录
	BackUser doLogin(String phone, String password)throws LogicException;
	
	// 获取后台用户列表(带角色名称)
	PageHolder<BackUser> getUserPage(IndexSearchFilter searchFilter);
	
}
