package cn.acol.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.security.core.authorize.AuthorizeConfigManager;
import cn.acol.security.core.properties.SecurityProperties;

/**
 * 
 * @author DaveZhang
 *
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	 private SecurityProperties securityProperties;
	@Autowired
	private AuthorizeConfigManager authorizeConfigManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/login.html")
			.loginProcessingUrl("/authentication/login")
			.and()
			.headers().frameOptions().disable()
			.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html")
			.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(userDetailsService)
				.and()
			.csrf().disable();
		authorizeConfigManager.config(http.authorizeRequests());
	}
	@Autowired
	private DataSource dataSource;
	 /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 系统在启动时会自动生成表
       //  tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;

    }
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		ScaParamsDto forObject = restTemplate.getForObject("http://localhost:8888/scada/014217091919/scaParams", ScaParamsDto.class);
		System.out.println(forObject);
	}
}
