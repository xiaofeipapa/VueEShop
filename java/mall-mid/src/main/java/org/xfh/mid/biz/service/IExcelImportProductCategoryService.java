package org.xfh.mid.biz.service;

import java.io.InputStream;

import org.xfh.dcore.ex.LogicException;
import org.xfh.mid.vo.ExcelImportResult;

public interface IExcelImportProductCategoryService {
	
	// 通过excel方式导入分类
	ExcelImportResult doImport(InputStream in) throws LogicException;
}
