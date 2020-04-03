package pl.jankowski.NightRidePlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan
//@EnableAutoConfiguration(exclude={SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.class})
@SpringBootApplication
@ComponentScan
public class NightRidePlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NightRidePlannerApplication.class, args);
	}

}
