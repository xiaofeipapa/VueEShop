package org.xfh.dcore.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.vo.AjaxResultVo;
import org.xfh.dcore.vo.PageInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 获取request 等属性
 */
public class DWebUtils {
	private static Logger log = LoggerFactory.getLogger(DWebUtils.class);

	private static String projectPath;
	private static String classpath;
	
	// 将json字符串转为数组
	public static <T> T convertJson(String jsonData) {

		return new Gson().fromJson(jsonData, new TypeToken<T>() {}.getType());
		
	}

	/**
	 * 获取web项目的根路径
	 * 
	 * @return
	 */
	public static String getWebRoot() {
		if (null == projectPath) {
			getClassPath();
			projectPath = classpath.substring(0, classpath.indexOf("/WEB-INF/classes"));
		}
		return projectPath;
	}

	/**
	 * 获取classpath类路径
	 * 
	 * @return
	 */
	public static String getClassPath() {
		if (null == classpath)
			classpath = DWebUtils.class.getResource("/").getPath();
		return classpath;
	}

	/**
	 * 获取当前request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			return servletRequestAttributes.getRequest();
		} catch (Exception e) {
			return null;
		}
	}

	public static HttpServletResponse getResponse() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			return servletRequestAttributes.getResponse();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			return servletRequestAttributes.getRequest().getSession();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当前session id
	 * 
	 * @return
	 */
	public static String getSessionId() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			return servletRequestAttributes.getSessionId();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取application
	 * 
	 * @return
	 */
	public static ServletContext getApplication() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			return getSession().getServletContext();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 添加request作用域存值
	 * 
	 * @param name
	 * @param value
	 */
	public static void setRequestAttr(String name, Object value) {
		getRequest().setAttribute(name, value);
	}

	/**
	 * 获取request作用域存值
	 * 
	 * @param name
	 * @param <T>
	 * @return
	 */
	public static <T> T getRequestAttr(String name) {
		return (T) getRequest().getAttribute(name);
	}

	/**
	 * 获取request作用域的存值，如果为空，则返回def
	 * 
	 * @param name
	 * @param def
	 * @param <T>
	 * @return
	 */
	public static <T> T getRequestAttr(String name, Object def) {
		Object value = getRequest().getAttribute(name);
		if (value == null)
			return (T) def;
		else
			return (T) value;
	}

	/**
	 * 删除request作用域的存值
	 * 
	 * @param name
	 */
	public static void removeRequestAttr(String name) {
		getRequest().removeAttribute(name);
	}

	/**
	 * 添加session作用域存值
	 * 
	 * @param name
	 * @param value
	 */
	public static void setSessionAttr(String name, Object value) {
		getSession().setAttribute(name, value);
	}

	/**
	 * 获取session作用域存值
	 * 
	 * @param name
	 * @param <T>
	 * @return
	 */
	public static <T> T getSessionAttr(String name) {
		Object o = getSession().getAttribute(name);
		if(o == null) {
			return null;
		}
		return (T)o;
	}

	/**
	 * 获取session作用域存值，如果为空则返回def
	 * 
	 * @param name
	 * @param def
	 * @param <T>
	 * @return
	 */
	public static <T> T getSessionAttr(String name, Object def) {
		Object value = getSession().getAttribute(name);
		if (value == null)
			return (T) def;
		else
			return (T) value;
	}

	/**
	 * 删除session作用域存值
	 * 
	 * @param name
	 */
	public static void removeSessionAttr(String name) {
		getSession().removeAttribute(name);
	}

	/**
	 * 添加application作用域存值
	 * 
	 * @param name
	 * @param value
	 */
	public static void setApplicationAttr(String name, Object value) {
		getApplication().setAttribute(name, value);
	}

	/**
	 * 获取application作用域存值
	 * 
	 * @param name
	 * @param <T>
	 * @return
	 */
	public static <T> T getApplicationAttr(String name) {
		return (T) getApplication().getAttribute(name);
	}

	/**
	 * 获取application作用域存值，如果为空则返回def
	 * 
	 * @param name
	 * @param def
	 * @param <T>
	 * @return
	 */
	public static <T> T getApplicationAttr(String name, Object def) {
		Object value = getApplication().getAttribute(name);
		if (value == null)
			return (T) def;
		else
			return (T) value;
	}

	/**
	 * 删除application作用域存值
	 * 
	 * @param name
	 */
	public static void removeApplicationAttr(String name) {
		getApplication().removeAttribute(name);
	}

	/**
	 * 获取请求uri。会过滤掉requestUri中的项目向下文，例如：/project/user/list.do 过滤后 /user/list.do
	 * 
	 * @return
	 */
	public static String getRequestUri() {
		HttpServletRequest request = DWebUtils.getRequest();
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (!contextPath.equals("/"))
			requestUri = requestUri.replace(contextPath, "");
		return requestUri;
	}

	/**
	 * 获取请求的完整url，也就是包含路径参数部分
	 * 
	 * @return
	 */
	public static String getRequestUrl() {
		HttpServletRequest request = DWebUtils.getRequest();
		StringBuffer requestUrl = request.getRequestURL();
		String queryString = request.getQueryString();
		if (DStringUtils.isNotEmpty(queryString))
			requestUrl.append("?").append(queryString);
		return requestUrl.toString();
	}

	/**
	 * 获取客户端的ip地址
	 * 
	 * @return
	 */
	public static String getRemoteIpAddr() {
		HttpServletRequest request = DWebUtils.getRequest();

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Buyer-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Buyer-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取当前运行函数的函数名
	 * 
	 * @return
	 */
	public static String getCurrMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		String methodName = e.getClassName() + "." + e.getMethodName();
		return methodName;
	}

	public static void main(String[] args) {
		String methodName = DWebUtils.getClassPath();
		System.out.println(methodName);
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(header);
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Buyer-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Buyer-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 对形如 http://localhost:8080/gz-merchant/demand/list 的url, 返回 /demand/list
	 * 
	 * @param request
	 */
	public static String getRelativeUrl() {
		HttpServletRequest request = getRequest();
		String fullUrl = request.getRequestURL().toString();
		int len = request.getContextPath().length();
		int start = fullUrl.indexOf(request.getContextPath());
		String relativeUrl = fullUrl.substring(start + len);

		return relativeUrl;
	}

	/**
	 * 根据 request parameter 的参数, 填充bean相应字段的值.
	 * 
	 * @param request
	 * @param bean
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T populateBeanFrom(HttpServletRequest request, Class<T> clazz,
			List<FieldCustomConverter> converters) {
		T bean = null;
		try {
			bean = (T) clazz.newInstance();
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String value = request.getParameter(name);
				map.put(name, value);

			}
			BeanUtils.populate(bean, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 获取同名的参数变量.
	 * 
	 * @param paramName
	 * @return
	 */
	public static List<String> getSameNameValues(HttpServletRequest request, String paramName) {

		String[] values = request.getParameterValues(paramName + "[]");
		if (values != null && values.length > 0) {
			return Arrays.asList(values);
		}

		// 空的情况, 取单个的值
		String value = request.getParameter(paramName);

		List<String> alist = new ArrayList<>();
		if (DStringUtils.isNotEmpty(value)) {
			alist.add(value);
		}
		return alist;
	}

	public static final String getParameter(String name) {
		HttpServletRequest request = DWebUtils.getRequest();
		String v = request.getParameter(name);
		if (DStringUtils.isEmpty(v) || "undefined".equalsIgnoreCase(v) || "null".equalsIgnoreCase(v)) {
			return "";
		}
		return v;
	}

	// 获取必须的参数
	public static final String getParameterRequired(String name, String msg) throws Exception {
		HttpServletRequest request = DWebUtils.getRequest();
		String v = getParameter(name);
		if (DStringUtils.isEmpty(v)) {
			throw new LogicException(msg);
		}
		return v;
	}

	// 从request获取日期类型的字符串, 转换成 date 格式, 然后设置到对象的同名 date 字段. 
	public static final  void setDateValue(Object data, String name, String format) throws Exception {

		String v = getParameter(name);
		if (DStringUtils.isEmpty(v)) {
			return;
		}
		RefUtils.setProperty(data, name, new SimpleDateFormat(format).parse(v));
	}


	public static final  String getString(String name) {
		String v = DWebUtils.getRequest().getParameter(name);
		if (DStringUtils.isEmpty(v) || "undefined".equalsIgnoreCase(v) || "null".equalsIgnoreCase(v)) {
			return "";
		}
		v = v.trim();
		return v;
	}

	public static final  String getStringRequired(String name, String msg) throws LogicException {
		String v = getString(name);
		if (CheckUtils.isEmpty(v))
			throw new LogicException(msg);
		return v;
	}

	public static final  int getIntRequired(String name, String msg) throws LogicException {
		String v = getString(name);
		if (CheckUtils.isEmpty(v))
			throw new LogicException(msg);
		return Integer.parseInt(v);
	}

	public static final  int getInt(String name) {
		String v = getString(name);
		if (CheckUtils.isEmpty(v))
			return -1;
		return Integer.parseInt(v);
	}

	public static final  Long getLong(String name) {
		String v = getString(name);
		if (CheckUtils.isEmpty(v))
			return -1l;
		return Long.parseLong(v);
	}
	
	public static final PageInfo getPageInfo() {
		int pageSize = getInt("pageSize");
		int pageNo = getInt("pageNo");
		// FIXME with sort info  
		return new PageInfo(pageNo, pageSize);
	}

	public static void printAjaxJson(String o) {
		HttpServletResponse response = getResponse();
		try {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 1);
			response.getWriter().write(o);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
		}
	}

	public static void ajaxSucc(Object data) {
		try {
			AjaxResultVo bean = new AjaxResultVo();
			bean.setError(null);
			bean.setData(data);

			printAjaxJson(new Gson().toJson(bean));
		} catch (Exception e) {
			
		}
	}

	public static void ajaxSucc() {
		ajaxSucc(null);
	}

	public static void ajaxFail(String error) {
		ajaxFail(error, null);
	}
	
	public static void ajaxFailWithData(String error, Object data) {
		try {
			AjaxResultVo bean = new AjaxResultVo();
			bean.setError(error);
			bean.setData(data);
			printAjaxJson(new Gson().toJson(bean));
		} catch (Exception e) {
		}
	}

	public static void ajaxFail(String error, String code) {

		try {
			AjaxResultVo bean = new AjaxResultVo();
			bean.setError(error);
			bean.setErrorCode(code);
			printAjaxJson(new Gson().toJson(bean));
		} catch (Exception e) {
		}
	}
	
}
