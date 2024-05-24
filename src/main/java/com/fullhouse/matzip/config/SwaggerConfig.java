package com.fullhouse.matzip.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://haenny.tistory.com/442
// https://velog.io/@im2sh/Spring-boot-Spring-boot-3-Swagger-3.0-%EC%84%A4%EC%A0%95
@OpenAPIDefinition(
        info = @Info(title = "matZip API 명세서",
                description = "fullhouse matzip API 명세서",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("matZip API 명세서")  // 그룹 이름을 설정한다.
                .pathsToMatch(paths)     // 그룹에 속하는 경로 패턴을 지정한다.
                .build();
    }
}

//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI openAPI(@Value("${springdoc.version}") String springDocVersion) {
//        Info info = new Info()
//                .title("Swagger API Test")
//                .version(springDocVersion)
//                .description("Swagger API 테스트 입니다.")
//                .contact(new Contact().name("haenny").email("haenny@tistory.com").url("https://haenny.tistory.com"));
//
//        return new OpenAPI()
//                .components(new Components())
//                .info(info);
//    }
//}
