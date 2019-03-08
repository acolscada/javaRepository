package cn.acol;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableEurekaClient
/*
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})*/
public class ScadaManageApp {
	public static void main(String[] args) {
		SpringApplication.run(ScadaManageApp.class, args);
	}
}
