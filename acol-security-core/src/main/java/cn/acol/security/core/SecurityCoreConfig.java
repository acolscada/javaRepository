package cn.acol.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import cn.acol.security.core.properties.SecurityProperties;

/**
 * 
 * @author DaveZhang
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@Order(101)
public class SecurityCoreConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public PasswordEncoder passwordEncoder() {
		//用户加密解密接口
		return new BCryptPasswordEncoder();
	}
}
