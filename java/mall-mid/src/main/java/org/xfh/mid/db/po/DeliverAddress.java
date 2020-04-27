package org.xfh.mid.db.po;

public class DeliverAddress {

	protected Long id;
	protected Long userId;			// 属于哪个用户
	protected String name;			// 用户名称
	protected String phone;			// 用户移动电话
	protected String email;	
	
	// ---- 关于地址的code, 见 DistrictData 表
	protected String provinceCode;		// 省份/直辖市的code	
	protected String provinceName;	// 省份/直辖市的名字, 如广东省
	protected String cityCode;		// 城市(辖区)的code 
	protected String cityName;		// 城市(辖区)的名字, 如深圳市
	protected String districtCode;	// 辖区的code
	protected String districtName;	// 辖区的名字, 如南山区
	
	
	protected String blockInfo;		// 地址除省市区之外的剩余部分
	protected String post;			// 邮编		
	protected String defaultFlag;	//是否为默认地址 : Y 默认, N 不是默认
	protected String addressCat;	//地址类型, 见 AddressCats
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
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
	public String getBlockInfo() {
		return blockInfo;
	}
	public void setBlockInfo(String blockInfo) {
		this.blockInfo = blockInfo;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	public String getAddressCat() {
		return addressCat;
	}
	public void setAddressCat(String addressCat) {
		this.addressCat = addressCat;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
