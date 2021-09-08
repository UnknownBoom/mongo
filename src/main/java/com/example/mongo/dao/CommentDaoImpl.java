package com.example.mongo.dao;

import com.example.mongo.domain.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl {
    private final MongoTemplate mongoTemplate;

    public Comment save(Comment comment) {
        return mongoTemplate.save(comment);
    }

    public Collection<Comment> saveAll(List<Comment> comments) {
        List<Comment> res = new ArrayList<>();
        for (Comment comment : comments) {
            res.add(mongoTemplate.save(comment));
        }
        return res;
    }
}
