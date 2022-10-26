package com.reactive.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    //Tiene que ser de tipo String porque es alfanumérico
    private String id;
    private String name;
    private String username;
    private String email;

}
