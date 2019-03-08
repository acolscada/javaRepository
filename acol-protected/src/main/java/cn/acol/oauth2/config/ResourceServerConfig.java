package cn.acol.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import cn.acol.oauth2.authorize.AuthorizeConfigManager;


/**
 * 
 * @author DaveZhang
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	
	public void configure(HttpSecurity http) throws Exception {
		authorizeConfigManager.config(http.authorizeRequests());
	}
}
