package cn.acol.oauth2.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import cn.acol.oauth2.authorize.AuthorizeConfigProvider;


@Component
@Order(Integer.MIN_VALUE+1)
public class SwaggerConfigProvider implements AuthorizeConfigProvider{

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// TODO Auto-generated method stub
		config.antMatchers("/v2/api-docs",
				"/swagger-resources", "/configuration/**", 
				"/swagger-ui.html", "/webjars/**",
				"/swagger-resources/**").permitAll();
		return false;
	}
	
}
