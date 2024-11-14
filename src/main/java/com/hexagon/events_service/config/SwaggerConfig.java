package com.hexagon.events_service.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi eventsApi() {
        return GroupedOpenApi.builder()
                .group("events")
                .packagesToScan("com.hexagon.events_service.controller")
                .build();
    }
}
