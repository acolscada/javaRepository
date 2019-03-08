package cn.acol.security.rbac.authorize;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import cn.acol.security.core.authorize.AuthorizeConfigProvider;

@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider{

	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// TODO Auto-generated method stub
		config.anyRequest().access("@rbacService.hasPermission(request, authentication)");
		return true;
	}

}
