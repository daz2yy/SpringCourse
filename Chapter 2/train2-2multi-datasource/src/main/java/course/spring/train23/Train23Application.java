package course.spring.train23;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 两种实现方式：
 * 1. 配置 @SpringBootApplication，并配置 @Primary 属性在 Properties 和 DataSource 上
 * 2. 排除 Spring Boot 的自动加载类，自己在使用的时候去指定
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class})
@Slf4j
public class Train23Application {

	public static void main(String[] args) {
		SpringApplication.run(Train23Application.class, args);
	}
	
	@Bean
//	@Primary // 设置默认 DataSource
	@ConfigurationProperties("datasource.foo")
	public DataSourceProperties fooDataSourceProperties() {
		return new DataSourceProperties();
	}
	@Bean
//	@Primary // 设置默认 DataSource
	public DataSource fooDataSource() {
		DataSourceProperties pro = fooDataSourceProperties();
		log.info("foo datasource: {}", pro.getUrl());
		return pro.initializeDataSourceBuilder().build();
	}
	@Bean
	@Resource
	public PlatformTransactionManager fooTxManager(DataSource fooDataSource) {
		return new DataSourceTransactionManager(fooDataSource);
	}
	
	@Bean
	@ConfigurationProperties("datasource.bar")
	public DataSourceProperties barDataSourceProperties() {
		return new DataSourceProperties();
	}
	@Bean
	public DataSource barDataSource() {
		DataSourceProperties pro = barDataSourceProperties();
		log.info("bar datasource: {}", pro.getUrl());
		return pro.initializeDataSourceBuilder().build();
	}
	@Bean
	@Resource
	public PlatformTransactionManager barTxManager(DataSource barDataSource) {
		return new DataSourceTransactionManager(barDataSource);
	}

}
