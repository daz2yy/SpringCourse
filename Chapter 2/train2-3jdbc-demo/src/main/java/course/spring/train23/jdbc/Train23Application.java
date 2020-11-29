package course.spring.train23.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@SpringBootApplication
public class Train23Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Train23Application.class, args);
	}
	
	@Autowired
	FooDao fooDao;
	@Autowired
	BatchFooDao batchFooDao;
	
	@Override
	public void run(String... args) throws Exception {
//		fooDao.insertData();
		batchFooDao.batchInsert_jdbc();
		batchFooDao.batchInsert_NameParamJdbc();
		fooDao.listData();
	}
	
	@Bean
	@Autowired
	public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
		return new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("FOO").usingGeneratedKeyColumns("ID");
	}
	
	// 第二种方式
	@Bean
	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
}
