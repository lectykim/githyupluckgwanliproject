package com.example.hyupup_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HyupupToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HyupupToolApplication.class, args);
	}

}
