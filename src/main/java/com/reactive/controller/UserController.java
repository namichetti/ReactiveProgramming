package com.reactive.controller;

import com.reactive.model.document.User;
import com.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Mono<ResponseEntity<Flux<User>>> findAll(){
       return Mono.just(ResponseEntity.ok(this.userService.findAll()))
               .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable String id){
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<?>> save(@RequestBody User user){
        return this.userService.save(user)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        return this.userService.findById(id)
                .flatMap(p-> this.userService
                   .deleteById(p.getId())
                        .then(Mono.just(ResponseEntity.ok().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateById(@RequestBody User user,@PathVariable String id){
        return this.userService.findById(id)
                .flatMap(p->{
                    p.setName(user.getName());
                    p.setUsername(user.getUsername());
                    p.setEmail(user.getEmail());
                    return this.userService.save(p);
        }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
