/**
 * 示例说明
 * 
 * OssObjectService是OSS Java SDK的示例程序，您可以修改endpoint、accessKeyId、accessKeySecret、bucketName后直接运行。
 * 运行方法请参考README。
 * 
 * 本示例中的并不包括OSS Java SDK的所有功能，详细功能及使用方法，请参看“SDK手册 > Java-SDK”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/preface.html?spm=5176.docoss/sdk/java-sdk/。
 * 
 * 调用OSS Java SDK的方法时，抛出异常表示有错误发生；没有抛出异常表示成功执行。
 * 当错误发生时，OSS Java SDK的方法会抛出异常，异常中包括错误码、错误信息，详细请参看“SDK手册 > Java-SDK > 异常处理”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/exception.html?spm=5176.docoss/api-reference/error-response。
 * 
 * OSS控制台可以直观的看到您调用OSS Java SDK的结果，OSS控制台地址是：https://oss.console.aliyun.com/index#/。
 * OSS控制台使用方法请参看文档中心的“控制台用户指南”， 指南的来链接地址是：https://help.aliyun.com/document_detail/oss/getting-started/get-started.html?spm=5176.docoss/user_guide。
 * 
 * OSS的文档中心地址是：https://help.aliyun.com/document_detail/oss/user_guide/overview.html。
 * OSS Java SDK的文档地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/install.html?spm=5176.docoss/sdk/java-sdk。
 * 
 */

package org.xfh.dcore.helper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xfh.dcore.utils.DateUtils;
import org.xfh.dcore.utils.FileUtil;
import org.xfh.dcore.vo.UploadFileDto;
import org.xfh.dcore.vo.UploadResultDto;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

@Component
public class AliyunOSSHelper {
    static final Logger logger = LoggerFactory.getLogger(AliyunOSSHelper.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。

    @Value("${endpoint}")
    private String endpoint = "";
    
    @Value("${accessKeyId}")
    private String accessKeyId = "";
    
    @Value("${accessKeySecret}")
    private String accessKeySecret = "";
    
    @Value("${bucketName}")
    private String bucketName = "";

	private OSSClient ossClient;
	
	private String urlPrefix;

	public void init(){
		if( ossClient == null ){
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			urlPrefix = "http://" + bucketName + "." + endpoint;
//			urlPrefix = "http://xmyun.zhoty.com";
		}
	}

	public void destroy(){
		if( ossClient != null )
			ossClient.shutdown();
		ossClient = null;
	}

	/**
	 * 上传文件
	 *
	 * @param uploadFileDto 要上传的文件对象
	 * @param userId 登录用户Id
	 *
	 * @step：
	 * 		//S1.记得调用前要先调用init() 方法，确保 OSS Buyer 存在
	 * 	    S2.判断OSS Buyer 组是否存在，不存在就创建一个
	 * 	    S3.判断文件对象的文件流是否存在
	 * 	    S4.调用 OSS Client对象的putObject 方法上传文件对象到 OSS 组里
	 * 		//S5.记得调用后要调用destroy() 方法，销毁 OSS Buyer
	 * */
	public UploadResultDto uploadFile(UploadFileDto uploadFileDto, String userId){
		// 日志配置，OSS Java SDK使用log4j记录错误信息。示例程序会在工程目录下生成“oss-demo.log”日志文件，默认日志级别是INFO。
		// 日志的配置文件是“conf/log4j.properties”，如果您不需要日志，可以没有日志配置文件和下面的日志配置。
		//PropertyConfigurator.configure("conf/log4j.properties");

		logger.info("Started fileName=" + uploadFileDto.getFileName() + ", size=" + uploadFileDto.getSize() + ",userId=" + userId);
		init();

		// 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
		// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
//		String firstKey = UUID.randomUUID().toString();
		String firstKey = DateUtils.currentTimeForString()+""+(int)((Math.random()*9+1)*1000);
		//图片名称用年月日+4个字符串
		String fileName = firstKey + "."+ FileUtil.getFileExtension(uploadFileDto.getFileName());
		logger.info("文件名："+firstKey);
		try {

			// 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			if (ossClient.doesBucketExist(bucketName)) {
				//System.out.println("您已经创建Bucket：" + bucketName + "。");
			} else {
				//System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
				// 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
				// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
				logger.debug("创建Bucket:" + bucketName);
				CreateBucketRequest request = new CreateBucketRequest(bucketName);
				request.setCannedACL(CannedAccessControlList.PublicRead);
				ossClient.createBucket(request);
			}

			// 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
			//BucketInfo info = ossClient.getBucketInfo(bucketName);
			//System.out.println("Bucket " + bucketName + "的信息如下：");
			//System.out.println("\t数据中心：" + info.getBucket().getLocation());
			//System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
			//System.out.println("\t用户标志：" + info.getBucket().getOwner());

			// 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
			// 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
			InputStream is = uploadFileDto.getInputStream();
			if( is == null )
				is = new ByteArrayInputStream(uploadFileDto.getDatas());

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(uploadFileDto.getType());
			meta.setContentLength(uploadFileDto.getSize());

			PutObjectResult result = ossClient.putObject(bucketName, fileName, is,meta);


			logger.debug("Object：" + fileName + "存入OSS成功。");
			
			UploadResultDto dto = new UploadResultDto();
			dto.setPath(this.makeUrlByOssId(fileName));
			
			return dto;

		} catch (Exception e) {
			e.printStackTrace();
			fileName = null;
			return null;
		}
	}

	/**
	 * 删除文件
	 * */
	public void deleteFile(String id){

		init();
		ossClient.deleteObject(bucketName, id);

	}
	
	public String makeUrlByOssId(String ossFileId){
		return this.urlPrefix + "/" + ossFileId;
	}

	public UploadFileDto getFile(String idd) {
		init();

		OSSObject ossObject = ossClient.getObject(bucketName, accessKeyId);
		UploadFileDto dto = new UploadFileDto();
		dto.setType(ossObject.getObjectMetadata().getContentType());
		dto.setSize(ossObject.getObjectMetadata().getContentLength());
		dto.setFileName(ossObject.getKey());
		dto.setInputStream(ossObject.getObjectContent());
		return dto;
	}

	
	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
}
