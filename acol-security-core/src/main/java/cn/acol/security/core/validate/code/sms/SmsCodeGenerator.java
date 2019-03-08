package cn.acol.security.core.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import cn.acol.security.core.properties.SecurityProperties;
import cn.acol.security.core.validate.code.ValidateCode;
import cn.acol.security.core.validate.code.ValidateCodeGenerator;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator{

	@Autowired
	private SecurityProperties securityProperties;
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		// TODO Auto-generated method stub
		String code = RandomStringUtils.randomNumeric(securityProperties.getValidateCodeProperties().getSmsCodeProperties().getLength());
		return new ValidateCode(code, securityProperties.getValidateCodeProperties().getSmsCodeProperties().getExpireIn());
	}
	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}
	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
	
	
}
