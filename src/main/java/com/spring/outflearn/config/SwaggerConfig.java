package com.spring.outflearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)  // 기본 세팅값인 200, 401, 402 등을 사용하지 않는다
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.outflearn.controller"))  // api 스펙이 작성되어 있는 패키지 (Controller) 를 지정
                .paths(PathSelectors.any()) // controller package 전부
//                .paths(PathSelectors.ant("/v1/**"))       // Controller 패키지 내 v1만 선택해서 할수도 있음
                .build()
                .apiInfo(apiInfo());    // Swagger UI 로 노출할 정보
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Outflearn API Documentation")
                .description("Outflearn 서버 API 설명을 위한 문서입니다.")
                .license("Outflearn")
                .version("1.0")
                .build();
    }
}
