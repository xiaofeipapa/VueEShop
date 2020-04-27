package org.xfh.web.config;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.xfh.dcore.utils.CheckUtils;
import org.xfh.dcore.utils.NumUtils;
import org.xfh.mid.db.po.DBs;

@ControllerAdvice("org.diego")
public class FrontDataBinding {
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
		// 前台json 必须传入 yyyy-MM-dd HH:mm:ss 的格式
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    boolean allowEmpty = true;
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, allowEmpty));
	    
	    // 前台的 bigdecimal 处理. 
	    CustomBigDecimalEditor bigDecimalEditor = new CustomBigDecimalEditor();
		binder.registerCustomEditor(BigDecimal.class, bigDecimalEditor);
		
		// 忽略这些字段
		String[] fields = new String[] {
			DBs.CREATE_TIME, DBs.UPDATE_TIME
		};
		binder.setDisallowedFields(fields);
	}
	private class CustomBigDecimalEditor extends PropertyEditorSupport {

		public void setAsText(String text) throws IllegalArgumentException {
			if (CheckUtils.isEmpty(text)) {
				setValue(null);
			} else {
				setValue(NumUtils.parse(text));
			}
		}
	}
}
