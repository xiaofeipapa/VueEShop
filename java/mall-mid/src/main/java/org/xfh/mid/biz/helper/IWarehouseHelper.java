package org.xfh.mid.biz.helper;

import java.util.List;

import org.xfh.mid.vo.UserCheckProductVo;

/**
 * 封装不涉及数据库的业务代码. 
 * 
 * @author cys
 *
 */
public interface IWarehouseHelper {
	
	void convertLabelList(List<UserCheckProductVo> voList);
	
	void convertLabel(UserCheckProductVo vo);
}
