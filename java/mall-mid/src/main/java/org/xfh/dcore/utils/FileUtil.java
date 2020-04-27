package org.xfh.dcore.utils;

import java.io.*;
import java.util.HashMap;

public class FileUtil {
	public static final HashMap typeMap = new HashMap();

	static {
		typeMap.put(".*", "application/octet-stream");
		typeMap.put(".001", "application/x-001");
		typeMap.put(".301", "application/x-301");
		typeMap.put(".323", "text/h323");
		typeMap.put(".906", "application/x-906");
		typeMap.put(".907", "drawing/907");
		typeMap.put(".a11", "application/x-a11");
		typeMap.put(".acp", "audio/x-mei-aac");
		typeMap.put(".ai", "application/postscript");
		typeMap.put(".aif", "audio/aiff");
		typeMap.put(".aifc", "audio/aiff");
		typeMap.put(".aiff", "audio/aiff");
		typeMap.put(".anv", "application/x-anv");
		typeMap.put(".asa", "text/asa");
		typeMap.put(".asf", "video/x-ms-asf");
		typeMap.put(".asp", "text/asp");
		typeMap.put(".asx", "video/x-ms-asf");
		typeMap.put(".au", "audio/basic");
		typeMap.put(".avi", "video/avi");
		typeMap.put(".awf", "application/vnd.adobe.workflow");
		typeMap.put(".biz", "text/xml");
		typeMap.put(".bmp", "application/x-bmp");
		typeMap.put(".bot", "application/x-bot");
		typeMap.put(".c4t", "application/x-c4t");
		typeMap.put(".c90", "application/x-c90");
		typeMap.put(".cal", "application/x-cals");
		typeMap.put(".cat", "application/vnd.ms-pki.seccat");
		typeMap.put(".cdf", "application/x-netcdf");
		typeMap.put(".cdr", "application/x-cdr");
		typeMap.put(".cel", "application/x-cel");
		typeMap.put(".cer", "application/x-x509-ca-cert");
		typeMap.put(".cg4", "application/x-g4");
		typeMap.put(".cgm", "application/x-cgm");
		typeMap.put(".cit", "application/x-cit");
		typeMap.put(".class", "java/*");
		typeMap.put(".cml", "text/xml");
		typeMap.put(".cmp", "application/x-cmp");
		typeMap.put(".cmx", "application/x-cmx");
		typeMap.put(".cot", "application/x-cot");
		typeMap.put(".crl", "application/pkix-crl");
		typeMap.put(".crt", "application/x-x509-ca-cert");
		typeMap.put(".csi", "application/x-csi");
		typeMap.put(".css", "text/css");
		typeMap.put(".cut", "application/x-cut");
		typeMap.put(".dbf", "application/x-dbf");
		typeMap.put(".dbm", "application/x-dbm");
		typeMap.put(".dbx", "application/x-dbx");
		typeMap.put(".dcd", "text/xml");
		typeMap.put(".dcx", "application/x-dcx");
		typeMap.put(".der", "application/x-x509-ca-cert");
		typeMap.put(".dgn", "application/x-dgn");
		typeMap.put(".dib", "application/x-dib");
		typeMap.put(".dll", "application/x-msdownload");
		typeMap.put(".doc", "application/msword");
		typeMap.put(".dot", "application/msword");
		
		typeMap.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
		typeMap.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		typeMap.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		typeMap.put(".txt", "text/plain");
		
		typeMap.put(".drw", "application/x-drw");
		typeMap.put(".dtd", "text/xml");
		typeMap.put(".dwf", "Model/vnd.dwf");
		typeMap.put(".dwf", "application/x-dwf");
		typeMap.put(".dwg", "application/x-dwg");
		typeMap.put(".dxb", "application/x-dxb");
		typeMap.put(".dxf", "application/x-dxf");
		typeMap.put(".edn", "application/vnd.adobe.edn");
		typeMap.put(".emf", "application/x-emf");
		typeMap.put(".eml", "message/rfc822");
		typeMap.put(".ent", "text/xml");
		typeMap.put(".epi", "application/x-epi");
		typeMap.put(".eps", "application/x-ps");
		typeMap.put(".eps", "application/postscript");
		typeMap.put(".etd", "application/x-ebx");
		typeMap.put(".exe", "application/x-msdownload");
		typeMap.put(".fax", "image/fax");
		typeMap.put(".fdf", "application/vnd.fdf");
		typeMap.put(".fif", "application/fractals");
		typeMap.put(".fo", "text/xml");
		typeMap.put(".frm", "application/x-frm");
		typeMap.put(".g4", "application/x-g4");
		typeMap.put(".gbr", "application/x-gbr");
		typeMap.put(".gcd", "application/x-gcd");
		typeMap.put(".gif", "image/gif");
		typeMap.put(".gl2", "application/x-gl2");
		typeMap.put(".gp4", "application/x-gp4");
		typeMap.put(".hgl", "application/x-hgl");
		typeMap.put(".hmr", "application/x-hmr");
		typeMap.put(".hpg", "application/x-hpgl");
		typeMap.put(".hpl", "application/x-hpl");
		typeMap.put(".hqx", "application/mac-binhex40");
		typeMap.put(".hrf", "application/x-hrf");
		typeMap.put(".hta", "application/hta");
		typeMap.put(".htc", "text/x-component");
		typeMap.put(".htm", "text/html");
		typeMap.put(".html", "text/html");
		typeMap.put(".htt", "text/webviewhtml");
		typeMap.put(".htx", "text/html");
		typeMap.put(".icb", "application/x-icb");
		typeMap.put(".ico", "image/x-icon");
		typeMap.put(".ico", "application/x-ico");
		typeMap.put(".iff", "application/x-iff");
		typeMap.put(".ig4", "application/x-g4");
		typeMap.put(".igs", "application/x-igs");
		typeMap.put(".iii", "application/x-iphone");
		typeMap.put(".img", "application/x-img");
		typeMap.put(".ins", "application/x-internet-signup");
		typeMap.put(".isp", "application/x-internet-signup");
		typeMap.put(".IVF", "video/x-ivf");
		typeMap.put(".java", "java/*");
		typeMap.put(".jfif", "image/jpeg");
		typeMap.put(".jpe", "image/jpeg");
		//typeMap.put(".jpe", "application/x-jpe");
		typeMap.put(".jpeg", "image/jpeg");
		typeMap.put(".jpg", "image/jpeg");
		//typeMap.put(".jpg", "application/x-jpg");
		typeMap.put(".js", "application/x-javascript");
		typeMap.put(".json", "application/json");
		typeMap.put(".jsp", "text/html");
		typeMap.put(".la1", "audio/x-liquid-file");
		typeMap.put(".lar", "application/x-laplayer-reg");
		typeMap.put(".latex", "application/x-latex");
		typeMap.put(".lavs", "audio/x-liquid-secure");
		typeMap.put(".lbm", "application/x-lbm");
		typeMap.put(".lmsff", "audio/x-la-lms");
		typeMap.put(".ls", "application/x-javascript");
		typeMap.put(".ltr", "application/x-ltr");
		typeMap.put(".m1v", "video/x-mpeg");
		typeMap.put(".m2v", "video/x-mpeg");
		typeMap.put(".m3u", "audio/mpegurl");
		typeMap.put(".m4e", "video/mpeg4");
		typeMap.put(".mac", "application/x-mac");
		typeMap.put(".man", "application/x-troff-man");
		typeMap.put(".math", "text/xml");
		typeMap.put(".mdb", "application/msaccess");
		typeMap.put(".mdb", "application/x-mdb");
		typeMap.put(".mfp", "application/x-shockwave-flash");
		typeMap.put(".mht", "message/rfc822");
		typeMap.put(".mhtml", "message/rfc822");
		typeMap.put(".mi", "application/x-mi");
		typeMap.put(".mid", "audio/mid");
		typeMap.put(".midi", "audio/mid");
		typeMap.put(".mil", "application/x-mil");
		typeMap.put(".mml", "text/xml");
		typeMap.put(".mnd", "audio/x-musicnet-download");
		typeMap.put(".mns", "audio/x-musicnet-stream");
		typeMap.put(".mocha", "application/x-javascript");
		typeMap.put(".movie", "video/x-sgi-movie");
		typeMap.put(".mp1", "audio/mp1");
		typeMap.put(".mp2", "audio/mp2");
		typeMap.put(".mp2v", "video/mpeg");
		typeMap.put(".mp3", "audio/mp3");
		typeMap.put(".mp4", "video/mpeg4");
		typeMap.put(".mpa", "video/x-mpg");
		typeMap.put(".mpd", "application/vnd.ms-project");
		typeMap.put(".mpe", "video/x-mpeg");
		typeMap.put(".mpeg", "video/mpg");
		typeMap.put(".mpg", "video/mpg");
		typeMap.put(".mpga", "audio/rn-mpeg");
		typeMap.put(".mpp", "application/vnd.ms-project");
		typeMap.put(".mps", "video/x-mpeg");
		typeMap.put(".mpt", "application/vnd.ms-project");
		typeMap.put(".mpv", "video/mpg");
		typeMap.put(".mpv2", "video/mpeg");
		typeMap.put(".mpw", "application/vnd.ms-project");
		typeMap.put(".mpx", "application/vnd.ms-project");
		typeMap.put(".mtx", "text/xml");
		typeMap.put(".mxp", "application/x-mmxp");
		typeMap.put(".net", "image/pnetvue");
		typeMap.put(".nrf", "application/x-nrf");
		typeMap.put(".nws", "message/rfc822");
		typeMap.put(".odc", "text/x-ms-odc");
		typeMap.put(".out", "application/x-out");
		typeMap.put(".p10", "application/pkcs10");
		typeMap.put(".p12", "application/x-pkcs12");
		typeMap.put(".p7b", "application/x-pkcs7-certificates");
		typeMap.put(".p7c", "application/pkcs7-mime");
		typeMap.put(".p7m", "application/pkcs7-mime");
		typeMap.put(".p7r", "application/x-pkcs7-certreqresp");
		typeMap.put(".p7s", "application/pkcs7-signature");
		typeMap.put(".pc5", "application/x-pc5");
		typeMap.put(".pci", "application/x-pci");
		typeMap.put(".pcl", "application/x-pcl");
		typeMap.put(".pcx", "application/x-pcx");
		typeMap.put(".pdf", "application/pdf");
		typeMap.put(".pdf", "application/pdf");
		typeMap.put(".pdx", "application/vnd.adobe.pdx");
		typeMap.put(".pfx", "application/x-pkcs12");
		typeMap.put(".pgl", "application/x-pgl");
		typeMap.put(".pic", "application/x-pic");
		typeMap.put(".pko", "application/vnd.ms-pki.pko");
		typeMap.put(".pl", "application/x-perl");
		typeMap.put(".plg", "text/html");
		typeMap.put(".pls", "audio/scpls");
		typeMap.put(".plt", "application/x-plt");
		typeMap.put(".png", "image/png");
		//typeMap.put(".png", "application/x-png");
		typeMap.put(".pot", "application/vnd.ms-powerpoint");
		typeMap.put(".ppa", "application/vnd.ms-powerpoint");
		typeMap.put(".ppm", "application/x-ppm");
		typeMap.put(".pps", "application/vnd.ms-powerpoint");
		typeMap.put(".ppt", "application/vnd.ms-powerpoint");
		typeMap.put(".ppt", "application/x-ppt");
		typeMap.put(".pr", "application/x-pr");
		typeMap.put(".prf", "application/pics-rules");
		typeMap.put(".prn", "application/x-prn");
		typeMap.put(".prt", "application/x-prt");
		typeMap.put(".ps", "application/x-ps");
		typeMap.put(".ps", "application/postscript");
		typeMap.put(".ptn", "application/x-ptn");
		typeMap.put(".pwz", "application/vnd.ms-powerpoint");
		typeMap.put(".r3t", "text/vnd.rn-realtext3d");
		typeMap.put(".ra", "audio/vnd.rn-realaudio");
		typeMap.put(".ram", "audio/x-pn-realaudio");
		typeMap.put(".ras", "application/x-ras");
		typeMap.put(".rat", "application/rat-file");
		typeMap.put(".rdf", "text/xml");
		typeMap.put(".rec", "application/vnd.rn-recording");
		typeMap.put(".red", "application/x-red");
		typeMap.put(".rgb", "application/x-rgb");
		typeMap.put(".rjs", "application/vnd.rn-realsystem-rjs");
		typeMap.put(".rjt", "application/vnd.rn-realsystem-rjt");
		typeMap.put(".rlc", "application/x-rlc");
		typeMap.put(".rle", "application/x-rle");
		typeMap.put(".rm", "application/vnd.rn-realmedia");
		typeMap.put(".rmf", "application/vnd.adobe.rmf");
		typeMap.put(".rmi", "audio/mid");
		typeMap.put(".rmj", "application/vnd.rn-realsystem-rmj");
		typeMap.put(".rmm", "audio/x-pn-realaudio");
		typeMap.put(".rmp", "application/vnd.rn-rn_music_package");
		typeMap.put(".rms", "application/vnd.rn-realmedia-secure");
		typeMap.put(".rmvb", "application/vnd.rn-realmedia-vbr");
		typeMap.put(".rmx", "application/vnd.rn-realsystem-rmx");
		typeMap.put(".rnx", "application/vnd.rn-realplayer");
		typeMap.put(".rp", "image/vnd.rn-realpix");
		typeMap.put(".rpm", "audio/x-pn-realaudio-plugin");
		typeMap.put(".rsml", "application/vnd.rn-rsml");
		typeMap.put(".rt", "text/vnd.rn-realtext");
		typeMap.put(".rtf", "application/msword");
		typeMap.put(".rtf", "application/x-rtf");
		typeMap.put(".rv", "video/vnd.rn-realvideo");
		typeMap.put(".sam", "application/x-sam");
		typeMap.put(".sat", "application/x-sat");
		typeMap.put(".sdp", "application/sdp");
		typeMap.put(".sdw", "application/x-sdw");
		typeMap.put(".sit", "application/x-stuffit");
		typeMap.put(".slb", "application/x-slb");
		typeMap.put(".sld", "application/x-sld");
		typeMap.put(".slk", "drawing/x-slk");
		typeMap.put(".smi", "application/smil");
		typeMap.put(".smil", "application/smil");
		typeMap.put(".smk", "application/x-smk");
		typeMap.put(".snd", "audio/basic");
		typeMap.put(".sol", "text/plain");
		typeMap.put(".sor", "text/plain");
		typeMap.put(".spc", "application/x-pkcs7-certificates");
		typeMap.put(".spl", "application/futuresplash");
		typeMap.put(".spp", "text/xml");
		typeMap.put(".ssm", "application/streamingmedia");
		typeMap.put(".sst", "application/vnd.ms-pki.certstore");
		typeMap.put(".stl", "application/vnd.ms-pki.stl");
		typeMap.put(".stm", "text/html");
		typeMap.put(".sty", "application/x-sty");
		typeMap.put(".svg", "text/xml");
		typeMap.put(".swf", "application/x-shockwave-flash");
		typeMap.put(".tdf", "application/x-tdf");
		typeMap.put(".tg4", "application/x-tg4");
		typeMap.put(".tga", "application/x-tga");
		typeMap.put(".tif", "image/tiff");
		typeMap.put(".tif", "application/x-tif");
		typeMap.put(".tiff", "image/tiff");
		typeMap.put(".tld", "text/xml");
		typeMap.put(".top", "drawing/x-top");
		typeMap.put(".torrent", "application/x-bittorrent");
		typeMap.put(".tsd", "text/xml");
		typeMap.put(".txt", "text/plain");
		typeMap.put(".uin", "application/x-icq");
		typeMap.put(".uls", "text/iuls");
		typeMap.put(".vcf", "text/x-vcard");
		typeMap.put(".vda", "application/x-vda");
		typeMap.put(".vdx", "application/vnd.visio");
		typeMap.put(".vml", "text/xml");
		typeMap.put(".vpg", "application/x-vpeg005");
		typeMap.put(".vsd", "application/vnd.visio");
		typeMap.put(".vsd", "application/x-vsd");
		typeMap.put(".vss", "application/vnd.visio");
		typeMap.put(".vst", "application/vnd.visio");
		typeMap.put(".vst", "application/x-vst");
		typeMap.put(".vsw", "application/vnd.visio");
		typeMap.put(".vsx", "application/vnd.visio");
		typeMap.put(".vtx", "application/vnd.visio");
		typeMap.put(".vxml", "text/xml");
		typeMap.put(".wav", "audio/wav");
		typeMap.put(".wax", "audio/x-ms-wax");
		typeMap.put(".wb1", "application/x-wb1");
		typeMap.put(".wb2", "application/x-wb2");
		typeMap.put(".wb3", "application/x-wb3");
		typeMap.put(".wbmp", "image/vnd.wap.wbmp");
		typeMap.put(".wiz", "application/msword");
		typeMap.put(".wk3", "application/x-wk3");
		typeMap.put(".wk4", "application/x-wk4");
		typeMap.put(".wkq", "application/x-wkq");
		typeMap.put(".wks", "application/x-wks");
		typeMap.put(".wm", "video/x-ms-wm");
		typeMap.put(".wma", "audio/x-ms-wma");
		typeMap.put(".wmd", "application/x-ms-wmd");
		typeMap.put(".wmf", "application/x-wmf");
		typeMap.put(".wml", "text/vnd.wap.wml");
		typeMap.put(".wmv", "video/x-ms-wmv");
		typeMap.put(".wmx", "video/x-ms-wmx");
		typeMap.put(".wmz", "application/x-ms-wmz");
		typeMap.put(".wp6", "application/x-wp6");
		typeMap.put(".wpd", "application/x-wpd");
		typeMap.put(".wpg", "application/x-wpg");
		typeMap.put(".wpl", "application/vnd.ms-wpl");
		typeMap.put(".wq1", "application/x-wq1");
		typeMap.put(".wr1", "application/x-wr1");
		typeMap.put(".wri", "application/x-wri");
		typeMap.put(".wrk", "application/x-wrk");
		typeMap.put(".ws", "application/x-ws");
		typeMap.put(".ws2", "application/x-ws");
		typeMap.put(".wsc", "text/scriptlet");
		typeMap.put(".wsdl", "text/xml");
		typeMap.put(".wvx", "video/x-ms-wvx");
		typeMap.put(".xdp", "application/vnd.adobe.xdp");
		typeMap.put(".xdr", "text/xml");
		typeMap.put(".xfd", "application/vnd.adobe.xfd");
		typeMap.put(".xfdf", "application/vnd.adobe.xfdf");
		typeMap.put(".xhtml", "text/html");
		typeMap.put(".xls", "application/vnd.ms-excel");
		typeMap.put(".xls", "application/x-xls");
		typeMap.put(".xlsx", "application/vnd.ms-excel");
		typeMap.put(".xlsx", "application/x-xls");
		typeMap.put(".xlw", "application/x-xlw");
		typeMap.put(".xml", "text/xml");
		typeMap.put(".xpl", "audio/scpls");
		typeMap.put(".xq", "text/xml");
		typeMap.put(".xql", "text/xml");
		typeMap.put(".xquery", "text/xml");
		typeMap.put(".xsd", "text/xml");
		typeMap.put(".xsl", "text/xml");
		typeMap.put(".xslt", "text/xml");
		typeMap.put(".xwd", "application/x-xwd");
		typeMap.put(".x_b", "application/x-x_b");
		typeMap.put(".x_t", "application/x-x_t");
		typeMap.put(".ceb", "application/x-ceb");
		typeMap.put(".cebx", "application/x-ceb");
		typeMap.put(".zip", "application/zip");
	}

	public static String getFileName(String path, String separator){
		if(path == null)
			return null;
		if(path.endsWith(separator))
			path = path.substring(0, path.length() - 1);
		if(path.indexOf(separator) == -1)
			return path;
		return path.substring(path.lastIndexOf(separator)+1, path.length());
	}
	
	public static String getContentType(String fileName) {
		String fileExt =  getFileExtension(fileName);
		if (fileExt.indexOf(".") < 0)
			fileExt = "." + fileExt;
		if (typeMap.containsKey(fileExt))
			return (String) typeMap.get(fileExt);
		else
			return (String) typeMap.get(".*");
	}
	
	public static String getContentType(File file) throws Exception{
		if (file==null||!file.exists()) {
			throw new Exception("文件" + file.getName() + "不存在");
		}
		String fileName = file.getName();
		return getFileExtension(fileName);
	}
	
	public static String getFileExtension(String fileName) {
		String retExt = "*";
		if (fileName.indexOf(".") > 0) {
			retExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName
					.length());
		}
		return retExt.toLowerCase();
	}
	
	public static ByteArrayOutputStream getFileAsByteArrayOutputStream(File file)throws Exception {		
		if (file==null||!file.exists()) {
			throw new Exception("文件" + file.getName() + "不存在");
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		int i;
		while ((i = bis.read()) != -1) {
			bos.write(i);
		}
		bos.flush(); // 提交文件流，很关键
		bis.close();
		return baos;
	}

	
	public static ByteArrayOutputStream getAsByteArrayOutputStream(InputStream fis)throws Exception {		
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		int i;
		while ((i = bis.read()) != -1) {
			bos.write(i);
		}
		bos.flush(); // 提交文件流，很关键
		bis.close();
		return baos;
	}
}
