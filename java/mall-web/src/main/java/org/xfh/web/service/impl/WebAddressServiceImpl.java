package org.xfh.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.web.service.IWebAddressService;

@Component
public class WebAddressServiceImpl extends BaseFrontService implements IWebAddressService{

    static final Logger logger = LoggerFactory.getLogger(WebAddressServiceImpl.class);

	@Transactional
	@Override
	public Long saveAddress(Long userId, DeliverAddress da) throws LogicException {
		
		da.setUserId(userId);
		
		VUtils.checkMandMax(da.getName(), "姓名", 40);
		VUtils.checkM(da.getDistrictCode(), "请选择省市区");
		VUtils.checkMandMax(da.getBlockInfo(), "详细地址", 100);		
		VUtils.checkM(da.getPhone(), "请输入手机号码或固定电话号码");
		
		// 有可能是固定电话
		if(CheckUtils.mightBePhone(da.getPhone())) {
			VUtils.checkMobile(da.getPhone(), "手机号码", true);			
		}else {
			VUtils.checkLengthRange(da.getPhone(), 7, 20, "固定电话必须大于7个且少于20个字符");
		}
		
		VUtils.checkEmail(da.getEmail(), 20);
		VUtils.checkPost(da.getPost());

		
		boolean isCreate = da.getId() == null;

		// 同一个电话号码和同一个地址组成唯一的记录
		String sql = DSqlUtils.andEqString(DBs.PHONE, da.getPhone(), true);
		sql += DSqlUtils.andEqString(DBs.NAME, da.getName(), true);
		sql += DSqlUtils.andEqString(DBs.BLOCK_INFO, da.getBlockInfo(), true);
		sql += DSqlUtils.andEqString(DBs.DISTRICT_CODE, da.getDistrictCode(), true);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, da.getAddressCat(), true);
		
		if( ! isCreate) {
			sql += DSqlUtils.andNe(DBs.ID, da.getId(), true);
		}

		int checkCount = daoHelper.getCountBySql(DeliverAddress.class, sql); 
		if( checkCount > 0) {
			throw new LogicException("已经存在相同的地址");
		}

		// 检查是否唯一地址
		sql = DSqlUtils.andEqNumber(DBs.USER_ID, da.getUserId(), true);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, da.getAddressCat(), true);

		int count = daoHelper.getCountBySql(DeliverAddress.class, sql);
		if(count < 1) {
			da.setDefaultFlag("Y");
		}
		
		Long pk;
		if(isCreate) {
			pk =daoHelper.insertOne(DeliverAddress.class, da);
		}else {
			daoHelper.updateById(DeliverAddress.class, da);
			pk = da.getId();
		}
		
		return pk;
	}

	@Transactional
	@Override
	public void setDefaultAddress(Long userId, Long id, String cat) {
		// 检查设置默认
		String sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, true);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, cat, true);

		// 更新其他记录为非默认
		DeliverAddress update = new DeliverAddress();
		update.setDefaultFlag("N");
		daoHelper.updateBySql(DeliverAddress.class, update, sql);
		
		// 设置本记录为Y
		update.setId(id);
		update.setDefaultFlag("Y");
		daoHelper.updateById(DeliverAddress.class, update);
		
		logger.info("=== 已设置默认, id: " + id);
				
	}

	@Override
	public List<DeliverAddress> getAddressList(Long userId, String cat) {
		String sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, cat, false);
		return daoHelper.getListBySql(DeliverAddress.class, sql);
	}

	@Override
	public List<DeliverAddress> deleteIt(Long userId, Long id) {
		String sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
		sql += DSqlUtils.andEqNumber(DBs.ID, id, true);
		
		DeliverAddress data = daoHelper.getOneBySql(DeliverAddress.class, sql);
		boolean isNotDefault = data.getDefaultFlag().equalsIgnoreCase("N");
		String cat = data.getAddressCat();
		
		daoHelper.deleteBySql(DeliverAddress.class, sql);
		
		List<DeliverAddress> dalist = this.getAddressList(userId, cat);
		
		if(isNotDefault) {
			return dalist;
		}
		
		// 随机设置一个默认地址
		if(CheckUtils.isNotEmpty(dalist)) {
			sql = DSqlUtils.andEqNumber(DBs.USER_ID, userId, false);
			sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, cat, false);
			DeliverAddress one = daoHelper.getOneBySql(DeliverAddress.class, sql);
			
			DeliverAddress update = new DeliverAddress();
			update.setDefaultFlag("Y");
			update.setId(one.getId());
			daoHelper.updateById(DeliverAddress.class, update);
			
			dalist = this.getAddressList(userId, cat);
		}
		
		return dalist;
	}
    
	
}
