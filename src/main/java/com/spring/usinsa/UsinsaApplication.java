package com.spring.usinsa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@EnableJpaAuditing
@SpringBootApplication
public class UsinsaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UsinsaApplication.class, args);
	}

	// OAuth 와의 통신을 위한 RestTemplate Bean
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
