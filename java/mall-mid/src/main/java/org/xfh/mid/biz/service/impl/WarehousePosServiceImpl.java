package org.xfh.mid.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xfh.dcore.ex.LogicException;
import org.xfh.dcore.service.impl.AbstractSingleTableService;
import org.xfh.dcore.utils.VUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.mid.biz.service.IWarehousePosService;
import org.xfh.mid.db.dao.WarehouseQueryDao;
import org.xfh.mid.db.po.WarehousePos;
import org.xfh.mid.vo.WarehousePosVo;

@Service
public class WarehousePosServiceImpl extends AbstractSingleTableService<WarehousePos> implements IWarehousePosService{

	private Logger logger = LoggerFactory.getLogger(WarehousePosServiceImpl.class);
	
	@Autowired
	WarehouseQueryDao warehouseQueryDao;

	@Override
	protected void checkBeforeCreateOrUpdate(WarehousePos entity, boolean isCreate) throws LogicException {

		VUtils.checkM(entity.getCode(), "自定义代号");
		VUtils.checkM(entity.getWarehouseId(), "请选择仓库");

		
		super.checkDuplicateByField("code", entity.getCode(), "自定义代号重复", entity.getId(), isCreate);
	}

	@Override
	public PageHolder<WarehousePosVo> getWarehousePosPage(IndexSearchFilter searchFilter) {

		int totalCount = super.getCountByFilter(searchFilter);
		List<WarehousePosVo> dataList = warehouseQueryDao.getWarehousePosPageList(searchFilter);
		
		PageHolder<WarehousePosVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(searchFilter.getPageSize());
		page.setPageNo(searchFilter.getPageNo());

		
		// 计算分页数量
		page.setPageCount(this.calculatePageCount(totalCount, searchFilter.getPageSize()));
		
		return page;
	}

}


