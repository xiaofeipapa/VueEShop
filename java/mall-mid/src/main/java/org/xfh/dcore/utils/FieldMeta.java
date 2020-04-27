package org.xfh.dcore.utils;

public class FieldMeta {
	String name;		// 字段名
	Class type;		// 字段类型, 如 java.lang.String
	
	public FieldMeta(String name, Class type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	
}
