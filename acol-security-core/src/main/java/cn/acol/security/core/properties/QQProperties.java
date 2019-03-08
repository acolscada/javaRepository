package cn.acol.security.core.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QQProperties{
	@Value("${cn.acol.security.core.properties.QQproperties.appId:}")
	private String appId;
	@Value("${cn.acol.security.core.properties.QQproperties.appSecret:}")
	private String appSecret;
	
	private String providerId = "QQ";
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	
	
}
