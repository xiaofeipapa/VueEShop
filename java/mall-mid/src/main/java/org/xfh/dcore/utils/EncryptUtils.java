package org.xfh.dcore.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtils {

	public static String decodeBase64(String base64String) {
		byte[] b = base64String.getBytes();
		Base64 base64 = new Base64();
		b = base64.decode(b);
		String s = new String(b);
		return s;
	}

	public static String encodeToMD5(String rawpass) {
		return DigestUtils.md5Hex(rawpass);
	}
	
	public static void main(String[] args) {
		String s = EncryptUtils.encodeToMD5("");
		System.out.println(s);
	}

}
