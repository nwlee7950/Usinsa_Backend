package com.spring.usinsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

@EnableJpaAuditing
@SpringBootApplication
public class UsinsaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UsinsaApplication.class, args);
	}

//	// OAuth 와의 통신을 위한 RestTemplate Bean
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}

	// RestTemplate 대신 WebClient 사용
	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("http://localhost") 	// 80 포트 사용중이므로 localhost:8080 이 아닌 localhost
				.build();
	}
}
