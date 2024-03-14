package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FootballPitchApplication {

	public static void main(String[] args) {

		SpringApplication.run(FootballPitchApplication.class, args);
	}

}
