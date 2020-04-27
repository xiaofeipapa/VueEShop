package org.xfh.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义配置类. 
 * 
 * @author cys
 *
 */
@SpringBootApplication
@Configuration
public class FrontWebConfig implements WebMvcConfigurer, WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    static final Logger logger = LoggerFactory.getLogger(FrontWebConfig.class);

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		// TODO Auto-generated method stub
		
	}

    @Value("${vueDomain}")
	String vueDomain;

	// ================ cros 设置
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		logger.debug("=== vueDomain: " + this.vueDomain);
		
		CorsRegistration cros = registry.addMapping("/**");
		cros.allowedOrigins(vueDomain);
		cros.allowCredentials(true);
		cros.allowedMethods("*");
		cros.allowedHeaders("*");
		
//		cros.allowedMethods("GET,POST,PUT");
//		cros.allowedHeaders("Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	}

}
