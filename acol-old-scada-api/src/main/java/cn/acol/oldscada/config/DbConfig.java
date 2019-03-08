package cn.acol.oldscada.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DbConfig {
	@Primary
	@Bean(name="primaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.primary")
	public DataSource primaryDataSource() {
		System.out.println("开始创建数据库----------------------");
		 DruidDataSource build = DruidDataSourceBuilder.create().build();
		//DataSource build = DataSourceBuilder.create().build();
		 System.out.println("成功创建数据库----------------------------------------");
		 return build;
	}
	@Bean(name="secondaryDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.secondary")
	public DataSource secondaryDataSource() {
		//DataSource build = DataSourceBuilder.create().build();
		 DruidDataSource build = DruidDataSourceBuilder.create().build();
		 build.setTestOnBorrow(false);
			build.setTestOnReturn(false);
			build.setTestWhileIdle(false);
		return build;
	}
	
	/*@Bean(name="primaryJdbcTemplate")
	public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
		
		return new JdbcTemplate(dataSource);
	}
	@Bean(name="secondaryDataSource")
	public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}*/
	
}
