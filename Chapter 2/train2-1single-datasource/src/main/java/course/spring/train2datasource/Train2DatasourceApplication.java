package course.spring.train2datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class Train2DatasourceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Train2DatasourceApplication.class, args);
	}
	
	@Autowired
	DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}
	
	public void showConnection() throws SQLException {
		System.out.println(dataSource.toString());
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();
	}
	
	public void showData() {
		jdbcTemplate.queryForList("SELECT * FROM FOO").forEach(System.out::println);
//		jdbcTemplate.queryForList("SELECT * FROM FOO").forEach(row -> System.out.println(row));
	}
}
