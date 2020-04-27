package org.xfh.mid.vo;

import java.util.List;

import org.xfh.mid.db.po.DistrictData;
/**
 * 区的信息, 其上的城市信息, 省份信息
 */
public class DistrictWithParentVo{
	
	String districtCode;
	String districtName;
	List<DistrictData> cityOptions;
	List<DistrictData> districtOptions;		// 同层级的区域列表

	String cityCode;
	String cityName;
	String provinceCode;
	String provinceName;
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public List<DistrictData> getCityOptions() {
		return cityOptions;
	}
	public void setCityOptions(List<DistrictData> cityOptions) {
		this.cityOptions = cityOptions;
	}
	public List<DistrictData> getDistrictOptions() {
		return districtOptions;
	}
	public void setDistrictOptions(List<DistrictData> districtOptions) {
		this.districtOptions = districtOptions;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
