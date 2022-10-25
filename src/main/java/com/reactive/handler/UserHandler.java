package com.reactive.handler;

import com.reactive.model.document.User;
import com.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserService service;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(service.findAll(), User.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return this.service.findById(id)
                .flatMap(p -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromValue(p)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        var user = request.bodyToMono(User.class);
        return user.flatMap(u -> ServerResponse
                .ok()
                .body(this.service.save(u), User.class));
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        var id = request.pathVariable("id");
        return this.service.findById(id)
                .flatMap(u -> this.service.deleteById(u.getId()).then(ServerResponse.ok().build())
                ).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        var user = request.bodyToMono(User.class);

       return this.service.findById(id)
                .zipWith(user, (u1, u2) -> {
                    u1.setName(u2.getName());
                    u1.setUsername(u2.getUsername());
                    u1.setEmail(u2.getEmail());
                    return u1;
                })
                .flatMap(u->ServerResponse.ok().body(this.service.save(u),User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
