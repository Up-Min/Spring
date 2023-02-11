package com.trable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrableApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrableApplication.class, args);
	}

}
