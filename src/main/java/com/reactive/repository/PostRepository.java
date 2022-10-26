package com.reactive.repository;

import com.reactive.model.document.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PostRepository extends ReactiveMongoRepository<Post,String> {
}
