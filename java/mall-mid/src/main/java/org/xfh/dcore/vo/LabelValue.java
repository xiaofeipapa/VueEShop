package org.xfh.dcore.vo;

/**
 * 不想依赖其他文件夹的 LabelValueBean , 用冗余的方式在这个文件夹再加一个. 
 * @author cys
 *
 */
public class LabelValue {

	protected String label;
	protected Object value;
	protected String sign;		// + 或 - 或 o 显示金融信息用到
	
	public LabelValue() {
	}

	public LabelValue(String label, Object value) {
		super();
		this.label = label;
		this.value = value;
	}
	public LabelValue(String label, Object value, String sign) {
		super();
		this.label = label;
		this.value = value;
		this.sign = sign;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
