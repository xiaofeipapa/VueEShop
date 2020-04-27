package org.xfh.mid.biz.service.web;

import org.xfh.dcore.vo.SmsResult;

public interface ISmsService {

	// 发送注册/登录的验证码
	SmsResult sendLoginSms(String phone);
	
}
