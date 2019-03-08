package cn.acol.scada.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AcolConfiguration {
	/**
	 * 
	 * Rest template
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
		
		/*ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setGrantType("password");
		System.out.println("认证服务器地址：  "+accessTokenUri);
		resourceDetails.setAccessTokenUri(accessTokenUri+"/oauth/token");
		resourceDetails.setClientId("acol");
		resourceDetails.setClientSecret("acolsecurity");
		List<String> scopes = new ArrayList<>();
		scopes.add("read"); 
		scopes.add("write");
		scopes.add("trust");
		resourceDetails.setScope(scopes);
		//-- set Resource Owner info
		resourceDetails.setUsername("superAdmin");
		resourceDetails.setPassword("123456");
		return new OAuth2RestTemplate(resourceDetails);*/
	}
}
