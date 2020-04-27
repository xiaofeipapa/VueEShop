package org.xfh.mid.biz.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.xfh.dcore.vo.excel.ColumnProp;

public interface ISupplierOrderExcelService{
	
	// 导出excel
	SXSSFWorkbook export(String supplierName,List<Map<String,String>> dataList, List<ColumnProp> columnProps);

	// 写入response
	void writeToResponse(SXSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response,
			String fileName)throws Exception;
	
}
