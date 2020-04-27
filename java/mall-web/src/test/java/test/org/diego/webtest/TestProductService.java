package test.org.diego.webtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.xfh.mid.vo.web.WebProductVo;
import org.xfh.web.service.IWebProductService;

@RunWith(SpringRunner.class)
@ComponentScan({"org.diego,test.org.diego,org.diego"})
@SpringBootTest()
public class TestProductService {
	
	@Autowired
	IWebProductService productService;

	@Test
	public void doTest() throws Exception {
		
		Long id = 269l;
		
		WebProductVo vo = productService.getWebProductDetail(null, id);
		
		System.out.println(vo);
		System.out.println("==== ok");

	}
	
}
