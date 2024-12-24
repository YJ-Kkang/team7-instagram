package com.sparta.team7instagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team7InstagramApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team7InstagramApplication.class, args);
	}

}
