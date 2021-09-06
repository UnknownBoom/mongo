package com.example.mongo.service;

import com.example.mongo.dao.UserDaoImpl;
import com.example.mongo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {
    private final UserDaoImpl userDaoImpl;

    @Transactional
    public User findUserByUsername(String username){
        return userDaoImpl.findByUsername(username);
    }
}
