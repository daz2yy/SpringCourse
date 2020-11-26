package course.spring.train1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Train1Application {

	public static void main(String[] args) {
		SpringApplication.run(Train1Application.class, args);
	}

	@RequestMapping("hello")
	public String hello() {
		return "Hello World!";
	}
}
