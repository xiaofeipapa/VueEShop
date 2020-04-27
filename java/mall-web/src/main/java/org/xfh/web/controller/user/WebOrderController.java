package org.xfh.web.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.orderService.IOrderHandleFacade;
import org.xfh.mid.biz.service.IOrderQueryService;
import org.xfh.mid.biz.service.web.IWebOrderService;
import org.xfh.mid.biz.stockService.IStockChangeFacade;
import org.xfh.mid.enums.PayCats;
import org.xfh.mid.vo.BatchOptResult;
import org.xfh.mid.vo.PayCountdownVo;
import org.xfh.mid.vo.form.MakeOrderForm;
import org.xfh.mid.vo.index.OrderIndexSearchForm;
import org.xfh.mid.vo.web.WebUserOrderIndexVo;
import org.xfh.web.controller.BaseMallFrontController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 用户的收货/发票地址
 * 
 * @author cys
 *
 */
@RestController
public class WebOrderController extends BaseMallFrontController {
	
    static final Logger logger = LoggerFactory.getLogger(WebOrderController.class);

    @Autowired
    IStockChangeFacade stockFacade;

    @Autowired
    IOrderQueryService orderQueryService;
    
    @Autowired
    IWebOrderService orderService;
    
    @Autowired
    IOrderHandleFacade orderHandler;

    // 新建订单
	@RequestMapping("/userOrder/makeOrder")
	public void makeOrder(String jsonData) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);

		MakeOrderForm form = new Gson().fromJson(jsonData, new TypeToken<MakeOrderForm>() {}.getType());
		
		BatchOptResult result = stockFacade.makeOrderForFront(userId, form);
		if(result.isSucc()) {
			DWebUtils.ajaxSucc(result.getBizId());			
		}else {
			DWebUtils.ajaxFailWithData("验证错误", result);			
		}
	}

    // 获取倒计时信息
	@RequestMapping("/userOrder/getCountdown")
	public void getCountdown(String oid) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);

		PayCountdownVo vo = orderQueryService.getOrderCountdownInfo(userId, oid);

		DWebUtils.ajaxSucc(vo);		
	}

    // 获取订单分页
	@RequestMapping("/userOrder/getPage")
	public void getPage() throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		PageInfo pageInfo = DWebUtils.getPageInfo();
		String searchText = DWebUtils.getString("q");
		String searchStatus = DWebUtils.getString("cat");
		
		OrderIndexSearchForm form = new OrderIndexSearchForm();
		form.setPageNo(pageInfo.getPageNo());
		form.setPageSize(pageInfo.getPageSize());
		form.setSearchText(searchText);
		form.setSearchStatus(searchStatus);
		form.setFront(true);

		WebUserOrderIndexVo  page = orderQueryService.getUserOrders(userId, form);

		DWebUtils.ajaxSucc(page);		
	}

    // 支付订单
	@RequestMapping("/userOrder/payIt")
	public void payIt(String oid, Integer payStyle) throws Exception{
		
		PayCats payCat = null;;
		if(payStyle == 1) {
			payCat = PayCats.ZHIFUBAO;
		}
		else if (payStyle == 2) {
			payCat = PayCats.WX;				
		}
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		orderHandler.payOrder(userId, oid, payCat);
		DWebUtils.ajaxSucc();		
	}

    // 删除订单
	@RequestMapping("/userOrder/deleteIt")
	public void deleteIt(String oid) throws Exception{
		
		Long userId = sessionHelper.getLoginUserId(true);
		
		orderService.deleteOrder(userId, oid);
		DWebUtils.ajaxSucc();		
	}

}
