package org.xfh.mid.biz.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xfh.dcore.dao.CommonDaoHelper;
import org.xfh.dcore.vo.excel.ColumnProp;
import org.xfh.mid.biz.service.ISupplierOrderExcelService;
import org.xfh.mid.db.dao.SupplierDao;

@Component
public class SupplierOrderExcelServiceImpl implements ISupplierOrderExcelService {

	static final Logger logger = LoggerFactory.getLogger(SupplierOrderExcelServiceImpl.class);

	@Autowired
	SupplierDao supplierDao;

	@Autowired
	CommonDaoHelper daoHelper;

	/**
	 * Description: 1、将list中的data写入表格中
	 * 
	 */
	public SXSSFWorkbook export(String supplierName, List<Map<String,String>> dataList, List<ColumnProp> columnProps) {
		// 内存中保留 10000 条数据，以免内存溢出，其余写入 硬盘
		SXSSFWorkbook wb = new SXSSFWorkbook(2000);
		// 创建一个Excel的sheet
		Sheet sheet = wb.createSheet(supplierName);
		
		// 第一行数据头
		Row first = sheet.createRow(0);
		for (int k = 0; k < columnProps.size(); k++) {

			Cell cell = first.createCell(k);
//			CellStyle style = getColumnTopStyle(wb);
//			cell.setCellStyle(style);
			cell.setCellValue(columnProps.get(k).getName());
		}
		
		// 以下为数据内容
		for (int i = 0; i < dataList.size(); i++) {
			int rowIndex = i + 1;
			Row row = sheet.createRow(rowIndex);
			
			Map<String,String> rowData = dataList.get(i);
			for (int k = 0; k < columnProps.size(); k++) {

				Cell cell = row.createCell(k);
//				CellStyle style = getColumnTopStyle(wb);
//				cell.setCellStyle(style);

				String content = (String)rowData.get(columnProps.get(k).getField());
				if (content == null) {
					content = "";
				}
				cell.setCellValue(content);
			}
			
		}
		
		/*
		 * for (int k = 0; k < fieldNames.size(); k++ ) { // poi提供的自动调整每列的宽度,但是不兼容中文调整
		 * sheet.autoSizeColumn(k); }
		 */
		setSizeColumn(sheet, columnProps.size());
		return wb;
	}

	/**
	 * Description: 1、调整列宽,兼容中文
	 * 
	 * @param sheet
	 * @param size
	 * @author: yizl
	 * @date: Nov 1, 2018 11:31:56 AM
	 */
	private void setSizeColumn(Sheet sheet, int size) {
		for (int columnNum = 0; columnNum < size; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				Row currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}

				if (currentRow.getCell(columnNum) != null) {
					Cell currentCell = currentRow.getCell(columnNum);
					int length = currentCell.getStringCellValue().getBytes().length;
					if (columnWidth < length) {
						columnWidth = length;
					}
				}
			}

			// Excel的长度为字节码长度*256,*1.3为了处理数字格式
			columnWidth = (int) Math.floor(columnWidth * 256 * 1.3);
			// 单元格长度大于20000的话也不美观,设置个最大长度
			columnWidth = columnWidth >= 20000 ? 20000 : columnWidth;
			// 设置每列长度
			sheet.setColumnWidth(columnNum, columnWidth);
		}
	}

	/**
	 * Description: 1、通过浏览器workbook以流的形式输出,为了处理中文表名路是你吗问题.
	 * 
	 * @param workbook 文件对象
	 * @param request
	 * @param response
	 * @param fileName 文件名
	 * @author: yizl
	 * @date: Oct 30, 2018 1:06:27 PM
	 */
	public void writeToResponse(SXSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response,
			String fileName) {
		
	}

	/**
	 * Description: 1、列头单元格样式
	 * 
	 * @param workbook
	 * @return
	 * @author: yizl
	 * @date: Oct 30, 2018 1:22:44 PM
	 */
//	private CellStyle getColumnTopStyle(SXSSFWorkbook workbook) {
//
//		// 设置字体
//		Font font = workbook.createFont();
//		// 设置字体大小
//		font.setFontHeightInPoints((short) 11);
//		// 字体加粗
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 设置字体名字
//		font.setFontName("Courier New");
//		// 设置样式;
//		CellStyle style = workbook.createCellStyle();
//		// 设置底边框;
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		// 设置底边框颜色;
//		style.setBottomBorderColor(HSSFColor.BLACK.index);
//		// 设置左边框;
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		// 设置左边框颜色;
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		// 设置右边框;
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		// 设置右边框颜色;
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		// 设置顶边框;
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		// 设置顶边框颜色;
//		style.setTopBorderColor(HSSFColor.BLACK.index);
//		// 在样式用应用设置的字体;
//		style.setFont(font);
//		// 设置自动换行;
//		style.setWrapText(false);
//		// 设置水平对齐的样式为居中对齐;
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 设置垂直对齐的样式为居中对齐;
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		return style;
//	}

	/**
	 * Description: 1、设置表体的单元格样式
	 * 
	 * @param workbook
	 * @return
	 * @author: yizl
	 * @date: Oct 30, 2018 1:18:42 PM
	 */
//	private CellStyle getBodyStyle(SXSSFWorkbook workbook) {
//		// 创建单元格样式
//		CellStyle cellStyle = workbook.createCellStyle();
//		// 设置单元格居中对齐
//		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//		// 设置单元格居中对齐
//		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
//		// 创建单元格内容不显示自动换行
//		cellStyle.setWrapText(false);
//		// 设置单元格字体样式
//		XSSFFont font = (XSSFFont) workbook.createFont();
//		font.setFontName("Courier New");// 设置字体
//		font.setFontHeight(11);// 设置字体的大小
//		cellStyle.setFont(font);// 将字体添加到表格中去
//		// 设置单元格边框为细线条
//		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
//		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		return cellStyle;
//	}

}
