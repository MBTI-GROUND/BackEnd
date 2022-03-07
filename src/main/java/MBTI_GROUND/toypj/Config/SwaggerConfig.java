package MBTI_GROUND.toypj.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("HOSE JOO","https://github.com/hose0728",
            "hose0728@naver.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
            .title("MBTI_GROUND API 명세서")
            .description("MBTI_GROUND REST API 명세서입니다.")
            .version("1.0.0")
            .license("MBTI_GROUND")
            .contact(DEFAULT_CONTACT)
            .licenseUrl("https://github.com/MBTI-GROUND")
            .build();


    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(List.of("application/json"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .groupName("MBTI_GROUND")
                .select()
                .apis(RequestHandlerSelectors.basePackage("MBTI_GROUND.toypj.Api"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
}
