package pe.edu.uni.VidaFit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VidaFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidaFitApplication.class, args);
	}

}