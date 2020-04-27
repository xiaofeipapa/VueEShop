package org.xfh.mid.biz.service.web.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xfh.dcore.vo.SmsResult;
import org.xfh.mid.biz.service.web.ISmsService;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Component
public class SmsServiceImpl implements ISmsService {
    static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    
    static final String signName = "水滴外语";	// 放在配置文件不能中文

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private String accessKeyId;
    private String accessKeySecret;
    private String loginTemplate;
    
    @Value("${sms.loginTemplate}")
	public void setLoginTemplate(String loginTemplate) {
		this.loginTemplate = loginTemplate;
	}

    @Value("${sms.accessKeyId}")
	public void setAccessKeyId(String accessKeyId) {
    	this.accessKeyId = accessKeyId;
	}
    
    @Value("${sms.accessKeySecret}")
	public void setAccessKeySecret(String accessKeySecret) {
    	this.accessKeySecret = accessKeySecret;
	}

	private SmsResult sendSms(String phone, String template) throws Exception {
		// 生成6位数的随机验证码
		Random random = new Random();
		String verifyCode = (random.nextInt(100000) + 100000) + "";

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();

		// 必填:待发送手机号
		request.setPhoneNumbers(phone);

		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);

		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(template);

		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\"" + verifyCode + "\"}");

//		logger.debug("=== signName: " + signName);
//		logger.debug("=== template: " + template);

		SmsResult resDto = new SmsResult();

		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		String code = sendSmsResponse.getCode();

		if (code.equals("OK")) {
			resDto.setCode(verifyCode); // 返回验证码
		} 
		else {			
			logger.error("=== semd sms result code: " + code);
			resDto.setError("短信服务暂时不可用");
		}

		return resDto;
	}
	@Override
	public SmsResult sendLoginSms(String phone) {
		SmsResult vo = new SmsResult();
		
		try {
			vo = this.sendSms(phone, this.loginTemplate);
		} catch (Exception e) {
			logger.error("==== 短信发送失败", e);
			vo.setError("短信服务暂时不可用");
		}
		return vo;
	}

}
