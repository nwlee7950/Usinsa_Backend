package com.spring.outflearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OutflearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutflearnApplication.class, args);
	}

}
