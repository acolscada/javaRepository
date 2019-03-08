package cn.acol.security.core.authorize;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AcolAuthorizeConfigManager implements AuthorizeConfigManager{

	@Autowired
	private List<AuthorizeConfigProvider> authorizeConfigProviders;
	@Override
	public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
		// TODO Auto-generated method stub
		 boolean existAnyRequestConfig = false;
	        String existAnyRequestConfigName = null;
	        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
	            boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
	            if (existAnyRequestConfig && currentIsAnyRequestConfig) {
	                throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
	                        + authorizeConfigProvider.getClass().getSimpleName());
	            } else if (currentIsAnyRequestConfig) {
	                existAnyRequestConfig = true;
	                existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
	            }
	        }

	        if(!existAnyRequestConfig){
	            config.anyRequest().authenticated();
	        }
	}

}
