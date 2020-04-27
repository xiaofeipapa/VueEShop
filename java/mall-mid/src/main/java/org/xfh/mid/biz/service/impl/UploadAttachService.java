package org.xfh.mid.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.mid.db.po.UploadAttach;

@Component
public class UploadAttachService extends AbstractSingleTableService<UploadAttach>  {
    static final Logger logger = LoggerFactory.getLogger(UploadAttachService.class);
	
	// 将业务主表数据和附件表关联起来. 
	public void bindingAttachIf(Long dataId, String attachCat, List<Long> attachIds) throws Exception{
		
		// 在调用这个方法之前, dataId(业务表id) 和 attachId (附件id) 必定在数据库存在. 这个方法只是将两者绑定起来. 
		if(attachIds == null || attachIds.size() < 1) {
			return;
		}
		
		// 查找附件
		List<UploadAttach> dbList = this.getListInIds(attachIds);

		// 看看哪个数据需要绑定
		List<UploadAttach> shouldUpldateList = new  ArrayList<UploadAttach>();
		for(UploadAttach entity : dbList){
	
			if( ! DSqlUtils.isValid(entity.getDataId()) || !attachCat.equalsIgnoreCase(entity.getCat()) ){
				entity.setDataId(dataId);
				entity.setCat(attachCat);
				shouldUpldateList.add(entity);
			}
		}
		
		// 更新附件信息
		for(UploadAttach entity : shouldUpldateList){
			super.updateById(entity, null);
		}		
	}
	
	// 返回业务表的附件
	public List<UploadAttach> findAttachList(int dataId, String attachCat){

		String sql = " and dataId = " + dataId + " and cat = '" + attachCat + "'";
		List<UploadAttach> dbList = super.getListBySql(sql);
		
		return dbList;
	}

	@Override
	protected void checkBeforeCreateOrUpdate(UploadAttach entity, boolean isCreate) throws LogicException {
		// 上传附件不需要检查重复性		
	}

	// 将附件表里的数据删除, 阿里云的数据不动. 
	public void fakeDelete(Long id) throws Exception{
		this.deleteById(null, id);
	}
	
}
