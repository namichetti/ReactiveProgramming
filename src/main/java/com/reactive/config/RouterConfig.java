package com.reactive.config;

import com.reactive.controller.UserHanlder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private UserHanlder userHanlder;

    @Bean
    public RouterFunction<ServerResponse> routes(){
        return RouterFunctions.route(RequestPredicates.GET("/api/"),p->this.userHanlder.findAll())
                .andRoute(RequestPredicates.GET("/api/{id}"),userHanlder::findById)
                .andRoute(RequestPredicates.POST("/api/"),userHanlder::save)
                .andRoute(RequestPredicates.PUT("/api/{id}"),userHanlder::update)
                .andRoute(RequestPredicates.DELETE("/api/{id}"),userHanlder::deleteById);
    }

}
