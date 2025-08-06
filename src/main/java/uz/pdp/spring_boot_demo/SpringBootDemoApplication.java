package uz.pdp.spring_boot_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.pdp.spring_boot_demo.dto.MailingConfigDTO;

@SpringBootApplication
@EnableConfigurationProperties({
		MailingConfigDTO.class
})
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
