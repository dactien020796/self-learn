package com.tino.selflearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SelfLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfLearningApplication.class, args);
	}

}
