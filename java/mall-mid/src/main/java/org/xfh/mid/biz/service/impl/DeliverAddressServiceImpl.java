package org.xfh.mid.biz.service.impl;

import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.utils.VUtils;
import org.xfh.mid.biz.service.IDeliverAddressService;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.db.po.DeliverAddress;
import org.xfh.mid.enums.AddressCats;
import org.xfh.mid.vo.AddressVo;

@Service
public class DeliverAddressServiceImpl extends AbstractSingleTableService<DeliverAddress> implements IDeliverAddressService{

	private Logger logger = LoggerFactory.getLogger(DeliverAddressServiceImpl.class);

	@Transactional
	@Override
	protected void checkBeforeCreateOrUpdate(DeliverAddress entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getName(), "请输入联系人");
		VUtils.checkMax(entity.getName(), 20, "联系人");
		
		VUtils.checkM(entity.getPhone(), "请输入联系电话");
		VUtils.checkMax(entity.getPhone(), 20, "联系电话");

		VUtils.checkM(entity.getDistrictCode(), "请选择省市区");

		VUtils.checkM(entity.getBlockInfo(), "请输入街道地址");
		VUtils.checkMax(entity.getBlockInfo(), 200, "街道地址");

		VUtils.checkMax(entity.getPost(), 20, "邮政编码");

		// 同一个电话号码和同一个地址组成唯一的记录
		String sql = DSqlUtils.andEqString(DBs.PHONE, entity.getPhone(), true);
		sql += DSqlUtils.andEqString(DBs.BLOCK_INFO, entity.getBlockInfo(), true);
		sql += DSqlUtils.andEqString(DBs.DISTRICT_CODE, entity.getDistrictCode(), true);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, entity.getAddressCat(), true);
		
		super.checkDuplicateBySql(sql, "相同地址的相同手机号已经存在", entity.getId(), isCreate);
		

		// 检查设置默认
		sql = DSqlUtils.andEqNumber(DBs.USER_ID, entity.getUserId(), true);
		sql += DSqlUtils.andEqString(DBs.ADDRESS_CAT, entity.getAddressCat(), true);
		
		if("Y".equalsIgnoreCase(entity.getDefaultFlag())){
			// 更新其他记录为非默认
			
			TreeMap<String,Object> param = new TreeMap<>();
			param.put(DBs.DEFAULT_FLAG, "N");
			commonDao.updateBySql(this.tableName, param, sql);
		}else {
			int count = commonDao.getCountBySql(this.tableName, sql);
			if(count < 1) {
				entity.setDefaultFlag("Y");
			}
		}
	}

	@Override
	public AddressVo getDefaultAddress(Long userId, Long addressUserId, String addressUserCat) {
		
		String sql = DSqlUtils.andEqNumber(DBs.USER_ID, addressUserId, true);
		sql += DSqlUtils.andEqString(DBs.USER_CAT, addressUserCat, true);
		sql += DSqlUtils.andEqString(DBs.DEFAULT_FLAG, "Y", true);
		
		List<DeliverAddress> dalist = this.getListBySql(sql);
		AddressVo vo = new AddressVo();
		for(DeliverAddress da : dalist) {
			if(AddressCats.GOOD_ADDRESS.getValue().equalsIgnoreCase(da.getAddressCat())) {
				vo.setGoodAddress(da);
			}
			if(AddressCats.INVOICE_ADDRESS.getValue().equalsIgnoreCase(da.getAddressCat())) {
				vo.setInvoiceAddress(da);
			}
		}
		
		return vo;
	}

}


