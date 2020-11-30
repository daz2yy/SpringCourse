package course.spring.train23.transaction.programic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@Slf4j
public class Train23Application implements CommandLineRunner {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	TransactionTemplate transactionTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Train23Application.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		log.info("COUNT BEFORE TRANSACTION: {}", getCount());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
				log.info("COUNT IN TRANSACTION: {}", getCount());
				transactionStatus.setRollbackOnly();
			}
		});
		log.info("COUNT AFTER TRANSACTION: {}", getCount());
	}
	
	public long getCount() {
		return (long) jdbcTemplate.queryForList("SELECT COUNT(*) AS CNT FROM FOO")
				.get(0).get("CNT");
	}
}
