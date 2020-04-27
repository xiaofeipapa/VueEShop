package org.xfh.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DWebUtils;
import org.xfh.dcore.utils.EncryptUtils;
import org.xfh.dcore.utils.ImageCode;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.SmsResult;
import org.xfh.mid.biz.service.web.IWebUserService;
import org.xfh.mid.db.po.Buyer;
import org.xfh.web.vo.LoginUserVo;
import org.xfh.web.vo.SmsCheckVo;
import org.xfh.web.vo.WebLoginForm;
import org.xfh.web.vo.WebUserSessionVo;

@RestController
public class WebLoginController extends BaseMallFrontController {

	static final Logger logger = LoggerFactory.getLogger(WebLoginController.class);

	static final String SESSION_VCODE = "SESSION_VCODE"; // 验证码对象
	static final String LOGIN_CHECK_FORM = "LOGIN_CHECK_FORM"; // 登录时的信息
	static final String IMAGE_CODE_SESS = "IMAGE_CODE_SESS"; // 图形验证码session
	static final int LOGIN_CHECK_OK = 1;
	static final int LOGIN_PASS_FAIL = 2; 		// 密码错误,但没有超过上限
	static final int LOGIN_PASS_FAIL_IC = 3; 	// 密码错误,且需要验证码
	static final int LOGIN_NO_USER = 4; 		// 用户不存在

	@Resource
	IWebUserService userService;

	@RequestMapping("/user/checkLogin")
	public void checkLogin() throws Exception {

		Long userId = sessionHelper.getLoginUserId(false);
		DWebUtils.ajaxSucc(userId != null);
	}

	@RequestMapping("/user/getVCode4Register")
	public void getVCode4Register(WebLoginForm form) throws Exception {

		VUtils.checkMobile(form.getPhone(), "手机号码", true);

		SmsResult vo = userService.getVCodeForRegister(form.getPhone());
		if (CheckUtils.isNotEmpty(vo.getError())) {
			DWebUtils.ajaxFail(vo.getError());
			return;
		}

		// 暂存验证码到session
		SmsCheckVo sessionVo = new SmsCheckVo();
		sessionVo.setVcode(vo.getCode());
		sessionVo.setPhone(form.getPhone());

		// 注意, 这个只是临时验证验证码的session, 并不是登录后的session
		HttpSession session = DWebUtils.getSession();
		// 先清除session里的验证码
		session.removeAttribute(SESSION_VCODE);
		session.setAttribute(SESSION_VCODE, sessionVo);

		DWebUtils.ajaxSucc(vo.getCode());
	}

	@RequestMapping("/user/register")
	public void register(WebLoginForm form) throws Exception {

		VUtils.checkSpecial(form.getAccount(), "用户名");
		VUtils.checkMobile(form.getPhone(), "手机号码", true);
		VUtils.checkM(form.getVcode(), "请输入验证码");
		VUtils.checkMandMax(form.getPassword(), "密码", 20);
		VUtils.checkM(form.getRePassword(), "请输入确认密码");

		if (!form.getPassword().equalsIgnoreCase(form.getRePassword())) {
			throw new LogicException("密码必须和确认密码不一致,请重新输入");
		}

		HttpSession session = DWebUtils.getSession();
		if (session == null || session.getAttribute(SESSION_VCODE) == null) {
			throw new LogicException("验证码无效,请重新获取验证码!");
		}

		String vcode = form.getVcode();
		SmsCheckVo checkVo = (SmsCheckVo) session.getAttribute(SESSION_VCODE);

		if (!form.getPhone().equals(checkVo.getPhone())) {
			session.removeAttribute(SESSION_VCODE);
			throw new LogicException("该手机号与接收验证码的手机号不一致!");
		}

		if (!vcode.equals(checkVo.getVcode())) {
			session.removeAttribute(SESSION_VCODE);
			throw new LogicException("验证码错误,请重新获取验证码!");

		}

		Buyer user = userService.doRegister(form.getPhone(), form.getAccount(), form.getPassword());
		session.removeAttribute(SESSION_VCODE);

		this.putIntoSession(user);

		DWebUtils.ajaxSucc(this.populateUserInfo(user));
	}

	/**
	 * 前台登陆 获取图片验证码
	 */
	@RequestMapping("/user/getImageCode")
	public void getImageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			response.setContentType("image/jpeg");
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			ImageCode imageCode = new ImageCode(100, 30, 5, 10);
			request.getSession().setAttribute(IMAGE_CODE_SESS, imageCode.getCode());

			imageCode.write(response.getOutputStream());
			logger.info("获取图片验证码={}", imageCode.getCode());
		} catch (IOException e) {
			logger.error("获取图片验证码错误", e);
		}
	}

	@RequestMapping("/user/logout")
	public void doLogout() throws Exception {

		if (sessionHelper.hasLogin()) {
			sessionHelper.logoutLoginUser();
		}

		logger.info(String.format("后台用户登出成功！"));
		DWebUtils.ajaxSucc(null);
	}

	@RequestMapping("/user/loginByAccount")
	public void loginByAccount() throws Exception {

		String accountOrPhone = DWebUtils.getParameterRequired("accountOrPhone", "请输入用户名或手机号");
		String password = DWebUtils.getParameterRequired("password", "请输入密码");
		String ic = DWebUtils.getParameter("ic");

		logger.info("尝试账号密码登陆:用户名/手机号={}", accountOrPhone);

		// 获取已经存在的login对象
		WebLoginForm form = this.getExistForm(accountOrPhone);

		int result = this.checkLoginInfo(accountOrPhone, password, ic, form);

		// 登录成功
		if (result == LOGIN_CHECK_OK) {
			Buyer user = userService.getUserByPhoneOrAccount(accountOrPhone);
			this.putIntoSession(user);
			DWebUtils.ajaxSucc(this.populateUserInfo(user));
			return;
		}

		// 验证密码出问题
		if (result == LOGIN_PASS_FAIL) {
			DWebUtils.ajaxFail("密码不正确", "PASS_NO");
			return;
		}

		// 验证密码不正确且下一次需要图形验证码
		if (result == LOGIN_PASS_FAIL_IC) {
			DWebUtils.ajaxFail("密码不正确", "PASS_NO_IC");
			return;
		}

		// 到这里是程序出问题
		logger.error("==== 验证登录出问题, result: " + result);
		DWebUtils.ajaxFail("服务器暂时不可用,请稍后再试");

	}

	private int checkLoginInfo(String accountOrPhone, String password, String ic, WebLoginForm form)
			throws LogicException {

		int maxAllow = 5;

		// 大于5次需要检查图形验证码
		if (form.getErrorCount() >= maxAllow) {
			VUtils.checkM(ic, "请输入图形验证码");
			// 此时图形验证码一定存在
			String existCode = DWebUtils.getSessionAttr(IMAGE_CODE_SESS);
			if (!ic.toString().equalsIgnoreCase(existCode)) {
				throw new LogicException("图形验证码不正确");
			}
		}

		// FIXME 大于5次之后, 如果输入图形验证码再出错两三次, 应该锁定该用户,4小时后才能登录.

		Buyer user = userService.getUserByPhoneOrAccount(accountOrPhone);
		if (user == null) {
			throw new LogicException("该用户还没有注册");
		}

		String enPwd = EncryptUtils.encodeToMD5(password);
		if (!user.getPassword().equals(enPwd)) {
			form.setErrorCount(form.getErrorCount() + 1);

			if (form.getErrorCount() >= maxAllow) {
				// 返回需要验证码的提示
				return LOGIN_PASS_FAIL_IC;
			}
			this.setCheckLoginForm(accountOrPhone, form);
			// 密码不正确
			return LOGIN_PASS_FAIL;
		}

		// 正确
		this.removeCheckInfo(accountOrPhone);
		return LOGIN_CHECK_OK;
	}

	private void setCheckLoginForm(String accountOrPhone, WebLoginForm form) {

		HttpSession session = DWebUtils.getSession();
		session.setAttribute(this.makeCheckLoginKey(accountOrPhone), form);
	}

	private void removeCheckInfo(String accountOrPhone) {

		HttpSession session = DWebUtils.getSession();

		// 清空检查信息
		session.removeAttribute(this.makeCheckLoginKey(accountOrPhone));

		// 清空验证码信息
		if (session.getAttribute(IMAGE_CODE_SESS) != null) {
			session.removeAttribute(IMAGE_CODE_SESS);
		}

	}

	private WebLoginForm getExistForm(String accountOrPhone) {

		HttpSession session = DWebUtils.getSession();
		WebLoginForm form = new WebLoginForm();
		Object o = session.getAttribute(this.makeCheckLoginKey(accountOrPhone));
		if (o != null) {
			form = (WebLoginForm) o;
		}
		return form;
	}

	private void putIntoSession(Buyer user) {

		WebUserSessionVo loginVo = new WebUserSessionVo();
		loginVo.setUserId(user.getId());
		loginVo.setAccount(user.getUser());

		HttpSession session = DWebUtils.getSession();
		session.setAttribute(WebUserSessionVo.SESSION_INFO_KEY, loginVo);
	}

	private String makeCheckLoginKey(String accountOrPhone) {
		return accountOrPhone + "_" + LOGIN_CHECK_FORM;
	}

	@RequestMapping("/user/getVCode4Login")
	public void getVCode4Login(String phone) throws Exception {

		VUtils.checkMobile(phone, "手机号码", true);

		SmsResult vo = userService.getVCodeForLogin(phone);
		if (CheckUtils.isNotEmpty(vo.getError())) {
			DWebUtils.ajaxFail(vo.getError());
			return;
		}

		// 暂存验证码到session
		SmsCheckVo sessionVo = new SmsCheckVo();
		sessionVo.setVcode(vo.getCode());
		sessionVo.setPhone(phone);

		// 注意, 这个只是临时验证验证码的session, 并不是登录后的session
		HttpSession session = DWebUtils.getSession();
		
		//先清除session里的验证码
		session.removeAttribute(SESSION_VCODE);
		session.setAttribute(SESSION_VCODE, sessionVo);

		DWebUtils.ajaxSucc(vo.getCode());
	}

	@RequestMapping("/user/loginByPhone")
	public void loginByPhone() throws Exception {

		String phone = DWebUtils.getParameterRequired("phone", "请输入手机号");
		String vcode = DWebUtils.getParameterRequired("vcode", "请输入验证码");

		VUtils.checkMobile(phone, "手机号码", true);
		VUtils.checkM(vcode, "请输入验证码");
		
		Buyer user = userService.getUserByPhoneOrAccount(phone);
		if(user == null) {
			throw new LogicException("该手机号还没注册");
		}

		HttpSession session = DWebUtils.getSession();
		if (session == null || session.getAttribute(SESSION_VCODE) == null) {
			throw new LogicException("验证码无效,请重新获取验证码!");
		}

		SmsCheckVo checkVo = (SmsCheckVo) session.getAttribute(SESSION_VCODE);

		if (!phone.equals(checkVo.getPhone())) {
			session.removeAttribute(SESSION_VCODE);
			throw new LogicException("该手机号与接收验证码的手机号不一致!");
		}

		if (!vcode.equals(checkVo.getVcode())) {
			session.removeAttribute(SESSION_VCODE);
			throw new LogicException("验证码错误,请重新获取验证码!");

		}

		session.removeAttribute(SESSION_VCODE);

		this.putIntoSession(user);

		DWebUtils.ajaxSucc(this.populateUserInfo(user));
	}
	
	private LoginUserVo populateUserInfo(Buyer user) {
		return new LoginUserVo(user.getUser(), user.getUserCat());
	}


	@RequestMapping("/user/changePass")
	public void changePass() throws Exception {
		
		Long userId = sessionHelper.getLoginUserId(true);

		String opass = DWebUtils.getStringRequired("opass", "请输入原密码");
		String newPass = DWebUtils.getStringRequired("newPass", "请输入新密码");
		String rePass = DWebUtils.getStringRequired("rePass", "请输入确认密码");
		
		userService.changePassword(userId, opass, newPass, rePass);

		DWebUtils.ajaxSucc();
	}
	
}
