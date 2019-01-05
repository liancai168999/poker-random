package com.bingo.utils.aibaba;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.bingo.config.AlibabaCloud;

import ch.qos.logback.classic.Logger;

/**
 * 文件上传
 * @author 谭振岳
 * @date 2018年10月11日
 */
@Component
public class UploadUtils {
    Logger logback = (Logger) LoggerFactory.getLogger(UploadUtils.class);
    
	private static OSSClient ossClient;
	
	/**
	 * 上传文件流
	 * @param objectName
	 * @throws FileNotFoundException
	 */
	public void uploadFile(String objectName,AlibabaCloud alibabaCloud,InputStream inputStream) throws FileNotFoundException {
		// 本公司使用hongkong，其它Region请按实际情况填写。
		String endpoint = alibabaCloud.getEndPoint();
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号
		String accessKeyId = alibabaCloud.getAccessKeyID();
		String accessKeySecret = alibabaCloud.getAccessKeySecret();
		// 创建OSSClient实例
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件流
		ossClient.putObject(alibabaCloud.getBucketName(), objectName, inputStream);
		// 关闭OSSClient
		ossClient.shutdown();
		
	}
	
	/**
	 * 获取文件访问路径
	 * @return
	 */
	public String getUrl(String objectName,AlibabaCloud alibabaCloud) {
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		URL url = ossClient.generatePresignedUrl(alibabaCloud.getBucketName(), objectName, expiration);
		return url.toString();
	}
	
}
