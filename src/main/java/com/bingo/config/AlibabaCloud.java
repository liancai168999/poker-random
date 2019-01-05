package com.bingo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云配置
 * @author 谭振岳
 * @date 2018年10月11日
 */
@ConfigurationProperties(prefix = "alibabacloud")
@Component
public class AlibabaCloud {

	public String EndPoint;
	public String AccessKeyID;
	public String AccessKeySecret;
	public String BucketName;
	public String ContentType;
	
	public String getEndPoint() {
		return EndPoint;
	}
	public void setEndPoint(String endPoint) {
		EndPoint = endPoint;
	}
	public String getAccessKeyID() {
		return AccessKeyID;
	}
	public void setAccessKeyID(String accessKeyID) {
		AccessKeyID = accessKeyID;
	}
	public String getAccessKeySecret() {
		return AccessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		AccessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return BucketName;
	}
	public void setBucketName(String bucketName) {
		BucketName = bucketName;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	@Override
	public String toString() {
		return "AlibabaCloud [EndPoint=" + EndPoint + ", AccessKeyID=" + AccessKeyID + ", AccessKeySecret="
				+ AccessKeySecret + ", BucketName=" + BucketName + ", ContentType=" + ContentType + "]";
	}
	
}
