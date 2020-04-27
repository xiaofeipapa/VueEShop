package org.xfh.mid.biz.service.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.EncryptUtils;
import org.xfh.dcore.vo.SmsResult;
import org.xfh.mid.biz.service.web.ISmsService;
import org.xfh.mid.biz.service.web.IWebUserService;
import org.xfh.mid.db.po.Buyer;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.enums.BuyUserCats;
import org.xfh.mid.enums.ClientGrades;

@Component
public class WebUserServiceImpl implements IWebUserService{
	
	@Autowired
	CommonDaoHelper daoHelper;
	
	@Autowired
	ISmsService sms;	

	@Transactional
	@Override
	public Buyer doRegister(String phone, String account, String password) throws LogicException {

		this.checkAccountExist(account);
		this.checkPhoneExists(phone);
		
		// 加密密码, 保存
		String enpass = EncryptUtils.encodeToMD5(password);
		
		Buyer user = new Buyer();
		user.setUserPhone(phone);
		user.setUser(account);
		user.setUserCat(BuyUserCats.FrontUser.getValue());
		user.setPassword(enpass);
		user.setDataStatus(Buyer.DATA_STATUS_IN_USE);
		user.setCreateTime(DateUtils.getChinaDate());
		user.setGrade(ClientGrades.NORMAL.getValue());
		user.setName(account);
		
		Long id = daoHelper.insertOne(Buyer.class, user);
		user.setId(id);
		
		return user;		
	}
	
	@Override
	public Buyer getUserByPhoneOrAccount(String phoneOrAccount) {
		
		String sql = DSqlUtils.andEqString(DBs.USER_CAT, BuyUserCats.FrontUser.getValue(), false);
		
		boolean isPhone = false;
		
		try {
			Long v = Long.valueOf(phoneOrAccount);
			sql = DSqlUtils.andEqNumber(DBs.USER_PHONE, v, false);
			isPhone = true;
		} catch (Exception e) {
		}
		
		// 如果不是手机就是账号
		if( ! isPhone) {
			sql = DSqlUtils.andEqString(DBs.USER, phoneOrAccount, true);
		}
		
		return daoHelper.getOneBySql(Buyer.class, sql);
	}

	private void checkAccountExist(String account) throws LogicException {
		
		String sql = DSqlUtils.andEqString(DBs.USER, account, false);
		sql += DSqlUtils.andEqString(DBs.USER_CAT, BuyUserCats.FrontUser.getValue(), false);
		Buyer user = daoHelper.getOneBySql(Buyer.class, sql);
		if(user != null) {
			throw new LogicException("该用户名已注册");
		}
		
	}
	
	private void checkPhoneExists(String phone) throws LogicException {
		
		String sql = DSqlUtils.andEqNumber(DBs.USER_PHONE, Long.valueOf(phone), false);
		sql += DSqlUtils.andEqString(DBs.USER_CAT, BuyUserCats.FrontUser.getValue(), false);
		Buyer user = daoHelper.getOneBySql(Buyer.class, sql);
		if(user != null) {
			throw new LogicException("该手机号已注册");
		}
		
	}

	@Override
	public SmsResult getVCodeForRegister(String phone) throws LogicException {

		//判断电话格式
		if(!CheckUtils.isMobileValid(phone)) {
			throw new LogicException("请输入合法的手机号");
		}
		
		this.checkPhoneExists(phone);
		
		return sms.sendLoginSms(phone);
	}

	@Override
	public SmsResult getVCodeForLogin(String phone) throws LogicException {
		//判断电话格式
		if(!CheckUtils.isMobileValid(phone)) {
			throw new LogicException("请输入合法的手机号");
		}

		String sql = DSqlUtils.andEqNumber(DBs.USER_PHONE, Long.valueOf(phone), false);
		sql += DSqlUtils.andEqString(DBs.USER_CAT, BuyUserCats.FrontUser.getValue(), false);
		Buyer user = daoHelper.getOneBySql(Buyer.class, sql);
		if(user == null) {
			throw new LogicException("该手机号还没注册");
		}
		
		return sms.sendLoginSms(phone);
	}

	@Override
	public void changePassword(Long userId, String opass, String npass, String cpass) throws LogicException {
		
		Buyer user = daoHelper.getById(Buyer.class, userId);
		
		opass = EncryptUtils.encodeToMD5(opass);
		
		if( ! user.getPassword().equals(opass)) {
			throw new LogicException("原密码输入不正确");				
		}
		
		if( ! npass.equals(cpass)){
			throw new LogicException("新密码必须和确认密码相同");
		}

		npass = EncryptUtils.encodeToMD5(npass);
		
		Buyer update = new Buyer();
		update.setPassword(npass);
		update.setId(userId);
		
		daoHelper.updateById(Buyer.class, update);		
		
	}
	

}
