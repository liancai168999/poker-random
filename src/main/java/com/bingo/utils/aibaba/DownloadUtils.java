package com.bingo.utils.aibaba;

import java.io.File;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.bingo.config.AlibabaCloud;

import ch.qos.logback.classic.Logger;

/**
 *  文件下载
 * @author 谭振岳
 * @date 2018年10月11日
 */
public class DownloadUtils {

	Logger  logger = (Logger) LoggerFactory.getLogger(DownloadUtils.class);
	
	@Autowired
	private AlibabaCloud alibabaCloud;
	
	public void downloadFile() {
		logger.info("");
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = alibabaCloud.getAccessKeyID();
		String accessKeySecret = alibabaCloud.getAccessKeySecret();
		
		// 创建OSSClient实例。
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
		ossClient.getObject(new GetObjectRequest(alibabaCloud.getBucketName(), ""), new File("<yourLocalFile>"));
		// 关闭OSSClient。
		ossClient.shutdown();
	}
	
}
