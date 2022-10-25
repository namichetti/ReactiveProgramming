package com.reactive.config;

import com.reactive.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Autowired
    private UserHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routes(){
        return route(RequestPredicates.GET("/api/v2/").or(RequestPredicates.GET("/api/")), handler::findAll)
                .andRoute(RequestPredicates.GET("/api/{id}"),handler::findById)
                .andRoute(RequestPredicates.POST("/api/"),handler::save)
                .andRoute(RequestPredicates.DELETE("/api/{id}"),handler::deleteById)
                .andRoute(RequestPredicates.PUT("/api/{id}"),handler::update);
    }

}
