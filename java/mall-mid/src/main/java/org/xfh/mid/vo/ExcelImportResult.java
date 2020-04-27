package org.xfh.mid.vo;

import java.util.List;

public class ExcelImportResult {
	List<String> excelErrors;
	List<String> dbErrors;
	int insertCount;
	public List<String> getExcelErrors() {
		return excelErrors;
	}
	public void setExcelErrors(List<String> excelErrors) {
		this.excelErrors = excelErrors;
	}
	public List<String> getDbErrors() {
		return dbErrors;
	}
	public void setDbErrors(List<String> dbErrors) {
		this.dbErrors = dbErrors;
	}
	public int getInsertCount() {
		return insertCount;
	}
	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}
}
