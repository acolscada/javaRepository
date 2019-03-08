package cn.acol.oauth2.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import cn.acol.oauth2.authorize.AuthorizeConfigProvider;
@Component
@Order(Integer.MIN_VALUE+2)
public class EurekaConfigProvider implements AuthorizeConfigProvider{

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// TODO Auto-generated method stub
		config.antMatchers("/actuator/info").permitAll();
		return false;
	}
}
