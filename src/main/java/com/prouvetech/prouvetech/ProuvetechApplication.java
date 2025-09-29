package com.prouvetech.prouvetech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.prouvetech.prouvetech")
@EntityScan(basePackages = "com.prouvetech.prouvetech.model")
@EnableJpaRepositories(basePackages = "com.prouvetech.prouvetech.repository")
public class ProuvetechApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProuvetechApplication.class, args);
	}

}
