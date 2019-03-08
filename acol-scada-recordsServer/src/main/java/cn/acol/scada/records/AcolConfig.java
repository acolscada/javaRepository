package cn.acol.scada.records;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AcolConfig {
	@Bean
	ScadaServer scadaServer() {
		ScadaServer scadaServer = new ScadaServer();
		new Thread(()-> scadaServer.start()).start();
		return scadaServer;
	}
}
