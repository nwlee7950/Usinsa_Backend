package com.spring.usinsa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UsinsaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UsinsaApplication.class, args);
	}

}
