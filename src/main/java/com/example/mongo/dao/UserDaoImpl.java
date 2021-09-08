package com.example.mongo.dao;


import com.example.mongo.domain.dto.UserAvgDto;
import com.example.mongo.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
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

    private Criteria byUsername(String username) {
        return Criteria.where("username").is(username);
    }

    public User findByUsername(String username) {
        Query query = new Query();
        Query whereUsername = query.addCriteria(byUsername(username));
        return mongoTemplate.findOne(
                whereUsername, User.class);
    }

    public Collection<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public Collection<User> findField(String field) {
        ProjectionOperation project = Aggregation.project(field);
        return mongoTemplate.aggregate(Aggregation.newAggregation(project), User.class, User.class).getMappedResults();
    }

    public User save(User user) {
        return mongoTemplate.insert(user);
    }

    // like replace
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

    public List<User> findByCommentMessage(String message) {
        Query query = new Query().addCriteria(Criteria.where("comments").elemMatch(Criteria.where("message").is(message)));
        return mongoTemplate.find(query, User.class);
    }

    public List<UserAvgDto> avgAgeByGroup(String groupField) {
        GroupOperation avg = Aggregation.group(groupField).avg("age").as("ageAvg");
        Aggregation aggregation = Aggregation.newAggregation(avg);
        return mongoTemplate.aggregate(aggregation, User.class, UserAvgDto.class).getMappedResults();
    }

    public List<UserAvgDto> avgAge() {
        AggregationOperation avg = Aggregation.group().avg("age").as("ageAvg");
        Aggregation aggregation = Aggregation.newAggregation(avg);
        return mongoTemplate.aggregate(aggregation, User.class, UserAvgDto.class).getMappedResults();
    }

    public List<Object> test() {
        AggregationOperation avg = Aggregation.group().avg("age").as("ageAvg");
        Aggregation aggregation = Aggregation.newAggregation(avg);
        return mongoTemplate.aggregate(aggregation, User.class, Object.class).getMappedResults();
    }
}

