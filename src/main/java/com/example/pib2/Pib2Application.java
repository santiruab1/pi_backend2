package com.example.pib2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class
})
public class Pib2Application {

	public static void main(String[] args) {
		SpringApplication.run(Pib2Application.class, args);
	}

}
