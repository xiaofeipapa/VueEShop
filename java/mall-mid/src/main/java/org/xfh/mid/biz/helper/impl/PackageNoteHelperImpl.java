package org.xfh.mid.biz.helper.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.mid.biz.helper.IPackageNoteHelper;
import org.xfh.mid.enums.AllocCats;
import org.xfh.mid.vo.UserOrderItemAllocInfoVo;

@Component
public class PackageNoteHelperImpl implements IPackageNoteHelper {

    static final Logger logger = LoggerFactory.getLogger(PackageNoteHelperImpl.class);

	@Override
	public List<Long> filterWarehouseIds(List<UserOrderItemAllocInfoVo> allocList) {
		
		List<Long> dataList = new ArrayList<>();
		for(UserOrderItemAllocInfoVo infoVo : allocList) {
			
			if(AllocCats.WAREHOUSE.getValue().equalsIgnoreCase(infoVo.getAllocCat()) 
					&& !dataList.contains(infoVo.getFromId())) {
				
				dataList.add(infoVo.getFromId());
				
			}
		}
		return dataList;
	}

	@Override
	public List<Long> filterSupplierIds(List<UserOrderItemAllocInfoVo> allocList) {

		List<Long> dataList = new ArrayList<>();
		for(UserOrderItemAllocInfoVo infoVo : allocList) {
			
			if(AllocCats.SUPPLIER.getValue().equalsIgnoreCase(infoVo.getAllocCat()) 
					&& !dataList.contains(infoVo.getFromId())) {
				
				dataList.add(infoVo.getFromId());
				
			}
		}
		return dataList;
	}
    

}
