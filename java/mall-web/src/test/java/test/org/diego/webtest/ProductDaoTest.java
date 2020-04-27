package test.org.diego.webtest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.xfh.mid.db.dao.SpecQueryDao;
import org.xfh.mid.db.po.ModalSpecValue;
import org.xfh.mid.vo.ModalSpecGroupVo;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@ComponentScan({"org.diego,test.org.diego,org.diego"})
@SpringBootTest()
public class ProductDaoTest {
	private Logger logger = LoggerFactory.getLogger(ProductDaoTest.class);

	@Autowired
	SpecQueryDao specQueryDao;

	@Test
	public void testCreate() throws Exception {

		Long modalId = 1012l;
		
		List<ModalSpecGroupVo> groupList = specQueryDao.getSpecGroupsWithChildren(modalId);
		for(ModalSpecGroupVo group : groupList) {

			System.out.println("==== group: " + group.getName());
			
			for(ModalSpecValue child: group.getChildren()) {
				System.out.println("-- child id: " + child.getId());
				System.out.println("-- child value: " + child.getValue());
				System.out.println("-- child order: " + child.getOrder());
				
			}
			
		}
		
		System.out.println("==== ok");
	}

//	@Test
//	public void testCreate() throws Exception {
//						
//		System.out.println("==== ok");
//	}

}
