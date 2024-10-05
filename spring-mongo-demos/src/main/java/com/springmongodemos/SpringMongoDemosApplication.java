package com.springmongodemos;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class SpringMongoDemosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMongoDemosApplication.class, args);
    }

    @Value("${swagger.enabled}")
    private boolean swaggerEnabled;

    @Bean
    // @Profile({"dev", "staging"}) us profile or app properties
    public GroupedOpenApi publicApi() {
        if (swaggerEnabled) {
            return GroupedOpenApi.builder()
                    .group("public")
                    .pathsToMatch("/**")
                    .build();
        }
        return null;
    }


}
