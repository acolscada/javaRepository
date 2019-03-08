package cn.acol.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.acol.security.core.properties.SecurityProperties;
import cn.acol.security.core.validate.code.image.ImageCodeGenerator;
import cn.acol.security.core.validate.code.sms.DefaultSmsCodeSender;
import cn.acol.security.core.validate.code.sms.SmsCodeSender;



@Configuration
public class VolidateCodeBeanConfig {
	@Autowired
	private SecurityProperties securityProperties;
	@Bean
	//@ConditionalOnMissingBean(name = "imageCodeGenerator") //如果找到这样一个bean 则不使用此bean
	public ValidateCodeGenerator imageCodeGenerator() {
		ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
		imageCodeGenerator.setSecurityProperties(securityProperties);
		return imageCodeGenerator;
	}
	
	@Bean
	//@ConditionalOnMissingBean(SmsCodeSender.class) //如果找到这样一个bean 则不使用此bean
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}
}
