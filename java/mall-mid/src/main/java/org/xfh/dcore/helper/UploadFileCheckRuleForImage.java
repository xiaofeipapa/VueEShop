package org.xfh.dcore.helper;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
import org.xfh.dcore.ex.LogicException;

public class UploadFileCheckRuleForImage implements IUploadFileCheckRule{
	
	long allowSize = 1024 * 1024 * 2; // 允许文件最大为 2M
	
	public void setAllowSize(long allowSize) {
		this.allowSize = allowSize;
	}
		
	public UploadFileCheckRuleForImage() {
		super();
	}


	public UploadFileCheckRuleForImage(long allowSize) {
		super();
		this.allowSize = allowSize;
	}


	@Override
	public void doCheck(MultipartFile file) throws Exception {
		
		InputStream inputStream = file.getInputStream();
		BufferedImage bi = ImageIO.read(inputStream);
		if (bi == null) {
			throw new LogicException("上传文件不符合图片格式");
		}

		if (file.getSize() > allowSize) {
			throw new LogicException("图片的大小不要超过2M");
		}

	}

}
