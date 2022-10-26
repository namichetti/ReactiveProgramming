package com.reactive.config;

import com.reactive.controller.PostHandler;
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
    private PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> routes(){
        return RouterFunctions.route(RequestPredicates.GET("/api/"),p->this.postHandler.findAll())
                .andRoute(RequestPredicates.GET("/api/{id}"),postHandler::findById)
                .andRoute(RequestPredicates.POST("/api/"),postHandler::save)
                .andRoute(RequestPredicates.PUT("/api/{id}"),postHandler::update)
                .andRoute(RequestPredicates.DELETE("/api/{id}"),postHandler::deleteById);
    }

}
