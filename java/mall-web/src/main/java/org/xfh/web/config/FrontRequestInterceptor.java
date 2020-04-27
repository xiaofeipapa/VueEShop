package org.xfh.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.vo.AjaxResultVo;

import com.google.gson.Gson;

/**
 * 1. 检查 session
 * 2. 设置页面信息,免去手动设置的麻烦.
 * 
 */
@Component
public class FrontRequestInterceptor implements HandlerInterceptor, InitializingBean{

    static final Logger logger = LoggerFactory.getLogger(FrontExceptionHandler.class);
	
	@Resource
	WebSessionHelper sessionHelper;
	
	// 这个属性决定了哪些url不需要登录就可以访问. 
	private List<String> exclude = new ArrayList<String>();


	@Override
	public void afterPropertiesSet() throws Exception {
		exclude.add("/user/login");
		exclude.add("/user/logout");
	}

	public void setSessionHelper(WebSessionHelper sessionHelper) {
		this.sessionHelper = sessionHelper;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String relativeUrl = DWebUtils.getRelativeUrl();

		if(logger.isDebugEnabled()){
//			logger.debug("====== request.getRequestURL(): " + request.getRequestURL().toString());
//			logger.debug("====== request.getContextPath(): " + request.getContextPath());
			//logger.debug("====== relativeUrl: " + relativeUrl);
		}		
				
		// 该路径不用检查
		if(exclude.contains(relativeUrl)){
			return true;
		}
		
		// 如果是ajax,不用设置页面信息.只需要检查session
		if( ! sessionHelper.hasLogin()){

			AjaxResultVo bean = new AjaxResultVo();
			bean.setErrorCode(AjaxResultVo.CODE_SESSION_EXPIRE);
			bean.setError("登录超时");
			DWebUtils.printAjaxJson(new Gson().toJson(bean));
		}

		return true;
	}
	
	
//	private BackMenuDto findMenu(UserSessionDto loginDto, int index){
//		for(BackMenuDto dto : loginDto.getUserMenus()){
//			if(dto.getIndex() == index){
//				return dto;
//			}
//		}
//		return null;	// 不太可能出现这个情况.
//	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
