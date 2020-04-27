package test.org.diego.webtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.xfh.mid.biz.service.web.ISmsService;

@RunWith(SpringRunner.class)
@ComponentScan({"org.diego,test.org.diego,org.diego"})
@SpringBootTest()
public class TestSms {
	
	@Autowired
	ISmsService service;

	@Test
	public void doTest() throws Exception {
		
		String phone = "18682300964";
		service.sendLoginSms(phone);
		
		System.out.println("==== ok");

	}
	
}
