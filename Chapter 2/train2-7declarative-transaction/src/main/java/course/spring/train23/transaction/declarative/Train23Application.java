package course.spring.train23.transaction.declarative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class Train23Application implements CommandLineRunner {
	@Autowired
	FooService fooService;
	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Train23Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		fooService.insertRecord();
		log.info("AAA {}",
				jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", long.class));
		
		try {
			fooService.insertThenRollback();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", long.class));
		}
		
		try {
			fooService.invokeInsertThenRollback();
		} catch (Exception e) {
			log.info("BBB222 {}",
					jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", long.class));
		}
	}
	
}
