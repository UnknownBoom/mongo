package com.example.mongo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("usr")
@Accessors(chain = true)
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private int age;
    private List<Comment> comments;
    private Comment comment;
    private String group;
    @TextIndexed
    private String largeText;
    @TextIndexed(weight = 2)
    private String secondLargeText;

    public User(User user) {
        if(Objects.nonNull(user)){
            this.id = user.getId();
            this.username = user.getUsername();
            this.age = user.getAge();
            this.comments = user.getComments();
            this.comment = user.getComment();
            this.group = user.getGroup();
            this.largeText = user.getLargeText();
            this.secondLargeText = user.getSecondLargeText();
        }
    }
}
