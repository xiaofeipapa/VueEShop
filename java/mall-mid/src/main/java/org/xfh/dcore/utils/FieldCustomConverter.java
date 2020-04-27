package org.xfh.dcore.utils;

public interface FieldCustomConverter {
	public String getFieldName();
	void convert(Object source, Object to);
}
