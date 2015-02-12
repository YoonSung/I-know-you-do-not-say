package gaongil.config;

import gaongil.dao.UserDao;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages={"gaongil.dao"})
@PropertySource("classpath:/database.properties")
public class DatabaseConfiguration {
	
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
}
