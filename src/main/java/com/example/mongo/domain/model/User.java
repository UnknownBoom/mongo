package com.example.mongo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("usr")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private int age;
    @DBRef
    private List<Comment> comments;
    @DBRef
    private Comment comment;
}
