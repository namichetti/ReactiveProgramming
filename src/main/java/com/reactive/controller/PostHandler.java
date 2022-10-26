package com.reactive.controller;

import com.reactive.model.document.Post;
import com.reactive.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class PostHandler {

    @Autowired
    private PostService postService;

    public Mono<ServerResponse> findAll() {
        return ServerResponse
                .ok()
                .body(this.postService.findAll(), Post.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return this.postService.findById(id)
                .flatMap(u -> ServerResponse
                        .ok()
                        .body(BodyInserters.fromValue(u)))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if (errorResponse.getStatusCode().equals(HttpStatus.NOT_FOUND))
                        return ServerResponse.notFound().build();
                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var post = serverRequest.bodyToMono(Post.class);
        return post.flatMap(u -> ServerResponse
                .ok()
                .body(this.postService.save(u), Post.class));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var post = serverRequest.bodyToMono(Post.class);
        var id = serverRequest.pathVariable("id");
        return post.flatMap(u -> this.postService.update(u, id))
                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if (errorResponse.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                        return ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(Collections.singletonMap("Mensaje de error personalizado","Not Found"));
                    return Mono.error(errorResponse);
        });
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return this.postService.findById(id)
                .flatMap(u -> this.postService.deleteById(u.getId()).then(ServerResponse.ok().build())
                ).onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if (errorResponse.getStatusCode().equals(HttpStatus.NOT_FOUND))
                        return ServerResponse.notFound().build();
                    return Mono.error(errorResponse);
                });
    }


}
