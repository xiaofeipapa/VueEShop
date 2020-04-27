package org.xfh.mid.biz.service.web.impl;

import org.springframework.stereotype.Component;
import org.xfh.mid.biz.service.web.IConfigService;
import org.xfh.mid.vo.web.WebStaticConfig;

@Component
public class ConfigServiceImpl implements IConfigService{

	@Override
	public WebStaticConfig getConfig() {
		WebStaticConfig config = new WebStaticConfig();
		
		config.setOfflineBankAccount("123 4567 8910 700");
		config.setOfflineBankFullName("xx银行xx支行");
		config.setOfflineBankUser("深圳市网***技术有限公司");
		
		return config;
	}

}
