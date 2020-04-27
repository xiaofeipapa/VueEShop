package org.xfh.mid.vo;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelExportVo {
	String name;
	SXSSFWorkbook book;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SXSSFWorkbook getBook() {
		return book;
	}
	public void setBook(SXSSFWorkbook book) {
		this.book = book;
	}
	public ExcelExportVo(String name, SXSSFWorkbook book) {
		super();
		this.name = name;
		this.book = book;
	}
	
}
