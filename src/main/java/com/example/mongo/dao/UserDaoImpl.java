package com.example.mongo.dao;


import com.example.mongo.domain.dto.UserDto;
import com.example.mongo.domain.model.Comment;
import com.example.mongo.domain.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserDaoImpl {
    private final MongoTemplate mongoTemplate;
    private final CommentDaoImpl commentDao;


    private Criteria byUsername(String username) {
        return Criteria.where("username").is(username);
    }

    public User findByUsername(String username) {
        Query query = new Query();
        Query whereUSername = query.addCriteria(byUsername(username));
        return mongoTemplate.findOne(
                whereUSername, User.class);
    }

    public Collection<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User save(User user) {
        if (user.getComments() != null) {
            commentDao.saveAll(user.getComments());
        }

        if (user.getComment() != null) {
            commentDao.save(user.getComment());
        }

        return mongoTemplate.save(user);
    }

    public User updateByUsername(String username, User user) {
        Query byUsername = new Query().addCriteria(byUsername(username));
        User one = mongoTemplate.findOne(byUsername, User.class);
        if (Objects.isNull(one)) return null;
        user.setId(one.getId());
        return mongoTemplate.save(user);
    }

    public User deleteByUsername(String username) {
        Query byUsername = new Query().addCriteria(byUsername(username));
        return mongoTemplate.findAndRemove(byUsername, User.class);
    }

    public List<UserDto> findByCommentMessage(String message) {
        Aggregation aggregation = Aggregation.newAggregation(
          Aggregation.unwind("$comment"),
                Aggregation.match(Criteria.where("comment.$message").is(message))
        );

        return mongoTemplate.aggregate(aggregation,User.class,UserDto.class).getMappedResults();
    }
}

