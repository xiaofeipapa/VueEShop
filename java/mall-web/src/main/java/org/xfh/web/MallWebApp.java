package org.xfh.web;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
@ComponentScan("org.xfh")
@MapperScan({"org.xfh.dcore.dao", "org.xfh.mid.db.dao"}) 
//@ImportResource(value= {"classpath:back-config.xml"})
public class MallWebApp implements CommandLineRunner {
		
    static final Logger logger = LoggerFactory.getLogger(MallWebApp.class);
    	
    @Override
    public void run(String... args) throws Exception {
    	
    }

	public static void main(String[] args) {
		
		SpringApplication.run(MallWebApp.class, args);
		logger.info("==== MallWebApp 已启动");
	}
}
