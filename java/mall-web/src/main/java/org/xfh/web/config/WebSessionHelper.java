package org.xfh.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.SessionInvalidException;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.web.vo.WebUserSessionVo;

@Component
public class WebSessionHelper {
	
	public boolean hasLogin(){
		HttpServletRequest request = DWebUtils.getRequest();
		HttpSession session = request.getSession();
		Object o = session.getAttribute(WebUserSessionVo.SESSION_INFO_KEY);
		return o != null;
	}

	// 获取用户session, 根据控制标志判断是否应该抛出异常. 
	public WebUserSessionVo getLoginUser(boolean throwExIfNull) throws SessionInvalidException{
		HttpServletRequest request = DWebUtils.getRequest();

		HttpSession session = request.getSession();
		Object o = session.getAttribute(WebUserSessionVo.SESSION_INFO_KEY);
		if(o == null){
			if(throwExIfNull) {
				throw new SessionInvalidException();				
			}else {
				return null;
			}
		}
		
		return (WebUserSessionVo)o;
	}

	public Long getLoginUserId(boolean throwExIfNull) throws SessionInvalidException{
		WebUserSessionVo dto = this.getLoginUser(throwExIfNull);
		if(dto == null)return null;
		return dto.getUserId();
	}
	
	
	public void setLoginUser(WebUserSessionVo result){
		HttpServletRequest request = DWebUtils.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute(WebUserSessionVo.SESSION_INFO_KEY, result);
	}
	
	public void logoutLoginUser(){
		HttpServletRequest request = DWebUtils.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(WebUserSessionVo.SESSION_INFO_KEY);
	}
}
