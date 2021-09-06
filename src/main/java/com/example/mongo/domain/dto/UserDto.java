package com.example.mongo.domain.dto;

import com.example.mongo.domain.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String username;
    private int age;
    private List<Comment> comments;
    private Comment comment;
}