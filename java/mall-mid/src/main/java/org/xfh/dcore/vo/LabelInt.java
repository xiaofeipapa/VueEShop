package org.xfh.dcore.vo;

/**
 * 不想依赖其他文件夹的 LabelValueBean , 用冗余的方式在这个文件夹再加一个. 
 * @author cys
 *
 */
public class LabelInt {

	protected String label;
	protected int value;
	
	public LabelInt() {
	}

	public LabelInt(String label, int value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
