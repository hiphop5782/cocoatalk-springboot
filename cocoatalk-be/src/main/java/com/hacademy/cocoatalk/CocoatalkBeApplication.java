package com.hacademy.cocoatalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CocoatalkBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocoatalkBeApplication.class, args);
	}

}
