package gaongil.config;

import java.util.Properties;

import gaongil.dao.UserDao;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@ComponentScan(basePackages={"gaongil.dao"})
@PropertySource("classpath:/database.properties")
public class DBConfig {
	
	@Autowired
	Environment environment;
	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		String driverClassName = environment.getProperty("database.driverClassName");
		String url = environment.getProperty("database.url");
		String username = environment.getProperty("database.username");
		String password = environment.getProperty("database.password");
		
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public UserDao userDao() {
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan("gaongil.domain");
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		
		String jpaDialect = "hibernate.dialect";
		String jpaFormatSql = "hibernate.format_sql";
		String jpaNamingStrategy = "hibernate.ejb.naming_strategy";
		String jpaShowSql = "hibernate.show_sql";
		
		Properties jpaProperties = new Properties();
		jpaProperties.put(jpaDialect, environment.getProperty(jpaDialect));
		jpaProperties.put(jpaFormatSql, environment.getProperty(jpaFormatSql));
		jpaProperties.put(jpaNamingStrategy, environment.getProperty(jpaNamingStrategy));
		jpaProperties.put(jpaShowSql, environment.getProperty(jpaShowSql));
		
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		return entityManagerFactoryBean;
	}
	
	@Bean
	public JpaTransactionManager transactionmanager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				entityManagerFactoryBean().getObject()
		);
		
		return transactionManager;
	}
}
