package org.xfh.mid.biz.stockService.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.DSqlUtils;
import org.xfh.dcore.vo.IndexSearchFilter;
import org.xfh.dcore.vo.PageHolder;
import org.xfh.dcore.vo.PageInfo;
import org.xfh.mid.biz.stockService.IStockQueryService;
import org.xfh.mid.db.po.DBs;
import org.xfh.mid.vo.WarehouseStockVo;
import org.xfh.mid.vo.index.StockIndexSearchForm;

@Component
public class StockQueryServiceImpl  extends AbstractStockService implements IStockQueryService{

	private Logger logger = LoggerFactory.getLogger(StockQueryServiceImpl.class);

	@Override
	public PageHolder<WarehouseStockVo> getWarehouseStockPage(Long userId, StockIndexSearchForm form) {
		
		IndexSearchFilter filter = new IndexSearchFilter(form.getSearchText(), 
				new PageInfo(form.getPageNo(), form.getPageSize()));

		if( CheckUtils.isNotEmpty(form.getWarehouseId())) {
			String moreSql = DSqlUtils.andEqNumber(DBs.WAREHOUSE_ID, Long.valueOf(form.getWarehouseId()), true);
			filter.setMoreSql(moreSql);
		}
		
		if(CheckUtils.isNotEmpty(form.getSearchText())) {
			filter.setLikeKeys(new String[] {"modalTitle"});
		}
		
		int totalCount = stockDao.getWarehouseStockCount(filter);
		List<WarehouseStockVo> dataList = stockDao.getWarehouseStockPageList(filter);

		PageHolder<WarehouseStockVo> page = new PageHolder<>();
		page.setTotalCount(totalCount);
		page.setDataList(dataList);
		page.setPageSize(filter.getPageSize());
		page.setPageNo(filter.getPageNo());

		// 计算分页数量
		page.setPageCount(DSqlUtils.calculatePageCount(totalCount, filter.getPageSize()));
		
		return page;
		
	}
	
	
	
}
