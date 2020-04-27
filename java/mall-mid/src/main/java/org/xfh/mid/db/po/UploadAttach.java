package org.xfh.mid.db.po;

import java.util.Date;

/**
 * 用于保存不确定上传文件数的附件.
 * 
 * @author cys
 *
 */
public class UploadAttach{

	public static final String BACK_USER_AVATAR = "BACK_USER_AVATAR";			// 用户头像 
	public static final String PRODUCT_BRAND_LOGO = "PRODUCT_BRAND_LOGO";		// 品牌logo 
	public static final String PRODUCT_IMAGE = "PRODUCT_IMAGE";					// 商品图片
	
	// 产品模型规格属性值的背景图
	public static final String PRODUCT_MODAL_SPEC_VALUE_IMAGE = "PRODUCT_MODAL_SPEC_VALUE_IMAGE";		
	Long id;
	String name;		// 上传文件名字
	String url;			// 上传文件的url
	Long size;				// 大小
	String contentType;		
	
	String cat;			// 用于区分业务主表, 如 BACK_USER_AVATAR 表示后台用户头像
	Long dataId;		// 业务主表的主键
	Long createUserId;
	Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
