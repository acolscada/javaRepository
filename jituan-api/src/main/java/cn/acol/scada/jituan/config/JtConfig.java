package cn.acol.scada.jituan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JtConfig {
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
