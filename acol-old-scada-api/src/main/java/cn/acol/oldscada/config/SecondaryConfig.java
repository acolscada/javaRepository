package cn.acol.oldscada.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef="entityManagerFactorySecondary",
        transactionManagerRef="transactionManagerSecondary",
        basePackages= {"cn.acol.oldscada.repository.secondary"}) //设置Repository所在位置
public class SecondaryConfig {
	 	@Autowired 
	    @Qualifier("secondaryDataSource")
	    private DataSource secordaryDataSource;

	    @Bean(name = "entityManagerSecordary")
	    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
	        return entityManagerFactorySecordary(builder).getObject().createEntityManager();
	    }

	    @Bean(name ="entityManagerFactorySecondary")
	    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecordary (EntityManagerFactoryBuilder builder) {
	        return builder
	                .dataSource(secordaryDataSource)
	                .properties(getVendorProperties())
	                .packages("cn.acol.oldscada.domain.secondary") //设置实体类所在位置
	                .persistenceUnit("secordaryPersistenceUnit")
	                .build();
	    }
	    @Autowired
	    private JpaProperties jpaProperties;

	    private Map<String, Object> getVendorProperties() {
	        return jpaProperties.getHibernateProperties(new HibernateSettings());
	    }
	    
	    @Bean(name = "transactionManagerSecordary")
	    public PlatformTransactionManager transactionManagerSecordary(EntityManagerFactoryBuilder builder) {
	        return new JpaTransactionManager(entityManagerFactorySecordary(builder).getObject());
	    }
}
