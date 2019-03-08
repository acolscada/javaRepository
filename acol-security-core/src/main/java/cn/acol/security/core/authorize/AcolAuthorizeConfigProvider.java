package cn.acol.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import cn.acol.security.core.properties.SecurityProperties;

@Configuration
@Order(Integer.MIN_VALUE)
public class AcolAuthorizeConfigProvider implements AuthorizeConfigProvider{

	@Autowired
	private SecurityProperties securityProperties;
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// TODO Auto-generated method stub
		config.antMatchers("/authentication/require","/device/**",
					securityProperties.getBrowser().getLoginPage(),
					"/code/*","/assets/**","/components/**").permitAll();
		System.out.println("允许登录页："+securityProperties.getBrowser().getLoginPage());
		return false;
	}

}
