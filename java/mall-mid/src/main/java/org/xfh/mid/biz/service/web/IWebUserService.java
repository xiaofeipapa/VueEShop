package org.xfh.mid.biz.service.web;

import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.vo.SmsResult;
import org.xfh.mid.db.po.Buyer;

public interface IWebUserService {
	
	// 获取注册验证码
	SmsResult getVCodeForRegister(String phone) throws LogicException;
	
	// 获取登录验证码
	SmsResult getVCodeForLogin(String phone) throws LogicException;
	
	// 注册用户
	Buyer doRegister(String phone, String account, String password)throws LogicException;
	
	// 根据用户名/手机号获取用户
	Buyer getUserByPhoneOrAccount(String phoneOrAccount);

	void changePassword(Long userId, String opass, String npass, String cpass) throws LogicException;
	
}
