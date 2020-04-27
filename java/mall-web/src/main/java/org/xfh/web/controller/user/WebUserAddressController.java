package org.xfh.web.controller.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.web.controller.BaseMallFrontController;
import org.xfh.web.service.IWebAddressService;

/**
 * 用户的收货/发票地址
 * 
 * @author cys
 *
 */
@RestController
public class WebUserAddressController extends BaseMallFrontController {
	
    static final Logger logger = LoggerFactory.getLogger(WebUserAddressController.class);

    @Autowired
    IWebAddressService addressService;

    // 保存收货地址
	@RequestMapping("/userAddress/saveDa")
	public void checkProducts(DeliverAddress da) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		Long id = addressService.saveAddress(userId, da);

		DWebUtils.ajaxSucc(id);
		
	}

    // 设置默认地址
	@RequestMapping("/userAddress/setDefault")
	public void setDefault(Long id, String cat) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		addressService.setDefaultAddress(userId, id, cat);

		DWebUtils.ajaxSucc();
		
	}

    // 获取收货地址列表
	@RequestMapping("/userAddress/getList")
	public void getList(String cat) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		List<DeliverAddress> addressList = addressService.getAddressList(userId, cat);

		DWebUtils.ajaxSucc(addressList);
		
	}

    // 删除
	@RequestMapping("/userAddress/deleteIt")
	public void deleteIt(Long id) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);

		List<DeliverAddress> addressList = addressService.deleteIt(userId, id);

		DWebUtils.ajaxSucc(addressList);
		
	}

	
}
