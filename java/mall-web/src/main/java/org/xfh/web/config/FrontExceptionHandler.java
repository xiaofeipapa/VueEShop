package org.xfh.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.ex.SessionInvalidException;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.vo.AjaxResultVo;

import com.google.gson.Gson;

/**
 * 处理逻辑异常,这样调用方不用再手动捕捉.
 * 
 * @author cys
 *
 */

@ControllerAdvice
public class FrontExceptionHandler {

    static final Logger logger = LoggerFactory.getLogger(FrontExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {

//		// ------- 处理业务异常
		if(ex instanceof LogicException){
			String message = ex.getMessage();
//			logger.warn("========== message");
			this.printLogicException(response, message);
			return null;
		}
		
		// ------- 处理登录异常
		if (ex instanceof SessionInvalidException){

			logger.warn("========== 用户session已超时");
			this.printSessionInvalidJson(response);
			return null;
		}		
		else{
			// 其他无法检测的exception
			logger.error("", ex);
			this.printLogicException(response, "服务器有点小问题,请联系管理员");
			return null;
		}
	  }

	protected void printSessionInvalidJson(HttpServletResponse response) {

		AjaxResultVo bean = new AjaxResultVo();
		bean.setError("登录超时");
		bean.setErrorCode(AjaxResultVo.CODE_SESSION_EXPIRE);
		DWebUtils.printAjaxJson(new Gson().toJson(bean));
	}

	protected void printLogicException(HttpServletResponse response, String message) {

		AjaxResultVo bean = new AjaxResultVo();
		bean.setError(message);
		bean.setErrorCode(AjaxResultVo.CODE_BIZ_EX);
		DWebUtils.printAjaxJson(new Gson().toJson(bean));
	}

}
