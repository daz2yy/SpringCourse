package course.spring.train23;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class Train23ApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
		Assertions.assertThrows(CustomDuplicatedKeyException.class, () -> {
			jdbcTemplate.update("INSERT INTO FOO (ID, BAR) VALUES (1, 'a')");
			jdbcTemplate.update("INSERT INTO FOO (ID, BAR) VALUES (1, 'b')");
		});
	}

}
