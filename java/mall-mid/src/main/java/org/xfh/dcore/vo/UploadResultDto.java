package org.xfh.dcore.vo;

import java.io.Serializable;

public class UploadResultDto implements Serializable {

	private static final long serialVersionUID = 5178442865375860570L;
	String id;
	String path;
	String originalFilename;
	String fileType;			// 如image, zip 等
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
}
