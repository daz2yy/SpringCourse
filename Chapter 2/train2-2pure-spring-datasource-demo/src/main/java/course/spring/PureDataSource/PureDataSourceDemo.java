package course.spring.PureDataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

@Configuration
public class PureDataSourceDemo {
    @Autowired
    DataSource dataSource;
    
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        showBeans(context);
        dataSourceDemo(context);
    }
    
    // 初始化 datasource
    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("driverClassName", "org.h2.Driver");
        properties.setProperty("url", "jdbc:h2:mem:testdb");
        properties.setProperty("username", "sa");
        return BasicDataSourceFactory.createDataSource(properties);
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource());
    }
    
    private static void dataSourceDemo(ApplicationContext context) throws SQLException {
        PureDataSourceDemo demo = context.getBean("pureDataSourceDemo", PureDataSourceDemo.class);
        demo.showDataSource();
    }
    
    private void showDataSource() throws SQLException {
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
    
    public static void showBeans(ApplicationContext context) {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
    }
}
