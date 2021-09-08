package com.example.mongo.controller;

import com.example.mongo.dao.UserDaoImpl;
import com.example.mongo.domain.dto.UserAvgDto;
import com.example.mongo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserDaoImpl userDao;

    @GetMapping("username/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userDao.findByUsername(username);
    }

    @GetMapping("comment/{message}")
    public Collection<User> findByCommentMessage(@PathVariable String message, @RequestParam(value = "fields", required = false) List<String> fields) {
        return userDao.findByCommentMessageWithFields(message, fields);
    }

    @GetMapping("")
    public Collection<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping("field/{field}")
    public Collection<User> findAllWithField(@PathVariable String field) {
        return userDao.findField(field);
    }

    @GetMapping("test")
    public Collection<Object> findAllWithField() {
        return userDao.test();
    }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @PutMapping("/username/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userDao.updateByUsername(username, user);
    }

    @GetMapping("avgAge/{groupField}")
    public Collection<UserAvgDto> avgAgeByGroup(@PathVariable String groupField) {
        return userDao.avgAgeByGroup(groupField);
    }

    @GetMapping("avgAge/all")
    public Collection<UserAvgDto> avgAge() {
        return userDao.avgAge();
    }

    @GetMapping("filter")
    public Collection<User> groupBy(@RequestParam Map<String, String> filterMap) {
        return userDao.filterBy(filterMap);
    }

    @DeleteMapping("/username/{username}")
    public User delete(@PathVariable String username) {
        return userDao.deleteByUsername(username);
    }

    @GetMapping("/text/{text}")
    public Collection<User> findText(@PathVariable String text) {
        return userDao.findText(text);
    }
}
