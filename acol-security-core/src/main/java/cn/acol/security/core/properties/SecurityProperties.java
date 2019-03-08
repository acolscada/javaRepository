package cn.acol.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "acol.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	private ValidateCodeProperties code = new ValidateCodeProperties();
	private SocialProperties social = new SocialProperties();
	

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getValidateCodeProperties() {
		return code;
	}

	public void setValidateCodeProperties(ValidateCodeProperties validateCodeProperties) {
		this.code = validateCodeProperties;
	}

	public SocialProperties getSocialProperties() {
		return social;
	}

	public void setSocialProperties(SocialProperties socialProperties) {
		this.social = socialProperties;
	}
	
	
	
	
}
