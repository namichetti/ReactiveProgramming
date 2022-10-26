package com.reactive.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    //Tiene que ser de tipo String porque es alfanumérico
    private String id;
    private String userId;
    private String title;
    private String body;
}
