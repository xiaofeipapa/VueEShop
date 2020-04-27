package org.xfh.dcore.utils;

import java.util.List;

/**
 * 用于生成简单的and sql条件
 * @author cys
 *
 */
public class AndFilter {

	StringBuilder sb = new StringBuilder();

	public AndFilter (String name, Object value, boolean escapeName) {
		this.andEqInternal(name, value, escapeName);
	}

	public AndFilter (String name, Object value) {
		this.andEqInternal(name, value, false);
	}
	
	public AndFilter andEq(String name, Object value, boolean escapeName) {

		this.andEqInternal(name, value, escapeName);
		return this;
	}
	
	public AndFilter andNe(String name, Object value, boolean escapeName) {

		String sql = DSqlUtils.andNe(name, value, escapeName);
		this.sb.append(sql);
		return this;
	}
	
	public AndFilter andIsNull(String name, boolean escapeName) {

		String sql = DSqlUtils.andIsNull(name, escapeName);
		this.sb.append(sql);
		return this;
	}
	
	public AndFilter andNotNull(String name, boolean escapeName) {

		String sql = DSqlUtils.andIsNotNull(name, escapeName);
		this.sb.append(sql);
		return this;
	}
	
	public AndFilter andInList(String name, List<Long> values, boolean escapeName) {

		if(escapeName) {
			name = " `" + name + "`";
		}
		
		String sql = DSqlUtils.andInValues(name, values);
		sb.append(sql);
		return this;
	}
	
	public String ok() {
		return this.sb.toString();
	}

	private void andEqInternal(String name, Object value, boolean escapeName) {

		if(escapeName) {
			name = " `" + name + "`";
		}

		if(value == null) {
			sb.append(" and " + name + " is null");
		}
		else {
			if(value instanceof Number) {
				sb.append(" and " + name + " = " + value);
			}
			if(value instanceof String) {
				sb.append(" and " + name + " = '" + value + "'");				
			}			
		}
	}
	
}
