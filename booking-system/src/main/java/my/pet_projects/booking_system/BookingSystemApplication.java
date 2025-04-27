package my.pet_projects.booking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
public class BookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingSystemApplication.class, args);
	}

}
