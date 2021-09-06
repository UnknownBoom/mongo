package com.example.mongo.controller;

import com.example.mongo.dao.UserDaoImpl;
import com.example.mongo.domain.dto.UserDto;
import com.example.mongo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public Collection<UserDto> findByCommentMessage(@PathVariable String message) {
        return userDao.findByCommentMessage(message);
    }

    @GetMapping("")
    public Collection<User> findAll() {
        return userDao.findAll();
    }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @PutMapping("/username/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userDao.updateByUsername(username, user);
    }

    @DeleteMapping("/username/{username}")
    public User delete(@PathVariable String username) {
        return userDao.deleteByUsername(username);
    }
}
