package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.EncryptUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IBackUserService;
import org.xfh.mid.db.dao.BackUserDao;
import org.xfh.mid.db.po.BackUser;

@Component
public class BackUserServiceImpl extends AbstractSingleTableService<BackUser> 
	implements IBackUserService{

	private Logger logger = LoggerFactory.getLogger(BackUserServiceImpl.class);
	
	@Autowired
	BackUserDao backUserDao;

	@Override
	protected void checkBeforeCreateOrUpdate(BackUser entity, boolean isCreate) throws LogicException {

		VUtils.checkMobile(entity.getPhone(), "手机号", true);
		VUtils.checkMandMax(entity.getRealName(), "姓名", 20);
		super.checkDuplicateByField("phone", entity.getPhone(), "手机号已经存在: " + entity.getPhone(), entity.getId(), isCreate);

	}
	
	public PageHolder<BackUser> getUserPage(IndexSearchFilter searchFilter){
		
		List<BackUser> mapList = backUserDao.getBackUserPage(searchFilter);
		int totalCount = backUserDao.getBackUserCount(searchFilter);
				
		PageHolder<BackUser> holder = new PageHolder<>();
		holder.setPageSize(searchFilter.getPageSize());
		holder.setPageNo(searchFilter.getPageNo());
		holder.setDataList(mapList);
		holder.setTotalCount(totalCount);
		
		// 计算分页数量
		holder.setPageCount(this.calculatePageCount(totalCount, searchFilter.getPageSize()));
		
		return holder;
		
	}

	@Override
	public BackUser doLogin(String phone, String password) throws LogicException {

		VUtils.checkM(phone, "请输入手机号");
		VUtils.checkM(password, "请输入密码");
		
		BackUser user = backUserDao.getUserByPhone(phone);
		if(user == null){
			throw new LogicException("此手机号关联的用户不存在");
		}
		
		String enPass = EncryptUtils.encodeToMD5(password);
		if( ! enPass.equals(user.getPassword())){
			throw new LogicException("密码不正确");
		}
		
		if(user.getDataStatus() == BackUser.DATA_STATUS_DISABLE){
			throw new LogicException("用户已被禁用,请联系管理员");
		}
		
		if(user.getDataStatus() == BackUser.DATA_STATUS_DELETE){
			throw new LogicException("用户处于删除状态,请联系管理员");
		}
		
		return user;
	}

}
