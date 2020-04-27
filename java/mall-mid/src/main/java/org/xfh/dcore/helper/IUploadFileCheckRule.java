package org.xfh.dcore.helper;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileCheckRule {
	void doCheck(MultipartFile file)throws Exception ;
}
