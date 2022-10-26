package com.reactive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${jsonplaceholder.url:-}")
    private String url;

    @Bean
    public WebClient registerWebclient(){
        return WebClient.create(this.url);
    }
}
