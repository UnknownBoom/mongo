package com.example.mongo.dao;


import com.example.mongo.domain.dto.UserAvgDto;
import com.example.mongo.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<User> findByCommentMessageWithFields(String message, List<String> fields) {
        Query query = new Query().addCriteria(Criteria.where("comments").elemMatch(Criteria.where("message").is(message)));
        if(Objects.nonNull(fields)){
            Criteria criteria = Criteria.where("comments").elemMatch(Criteria.where("message").is(message));
            MatchOperation match = Aggregation.match(criteria);
            Aggregation aggregation = Aggregation.newAggregation(match,Aggregation.project(fields.toArray(String[]::new)));
            return  mongoTemplate.aggregate(aggregation,User.class,User.class).getMappedResults();
        }else
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

    public List<Object> groupBy(List<String> fields) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group(fields.toArray(new String[1])));
        return mongoTemplate.aggregate(aggregation,User.class, Object.class).getMappedResults();
    }

    public List<User> filterBy(Map<String,String> filterMap) {
        Query query = new Query();
        Criteria criteria;
        for (var filter : filterMap.entrySet()) {
            try{
                int i = Integer.parseInt(filter.getValue());
                criteria = Criteria.where(filter.getKey()).is(i);
            }catch (Exception e){
                criteria = Criteria.where(filter.getKey()).is(filter.getValue());
            }

            query = query.addCriteria(criteria);
        }
        return mongoTemplate.find(query,User.class);
    }

    public Collection<User> findText(String textToSearch) {
        TextQuery textQuery = TextQuery.queryText(new TextCriteria().matchingAny(textToSearch)).sortByScore();
        return  mongoTemplate.find(textQuery,User.class);
    }

    public void removeAll() {
        mongoTemplate.remove(new Query(),User.class);
    }

    public Collection<User> insertAll(List<User> users) {
        return mongoTemplate.insertAll(users);
    }
}

