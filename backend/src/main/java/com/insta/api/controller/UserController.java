package com.insta.api.controller;

import com.insta.api.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class UserController {

    protected static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users/username/{username}")
    public Object getUserByName(@PathVariable("username") String username) {
        return userMapper.getUserByName(username);
    }

    @GetMapping("/users/userid/{userId}")
    public Object getUserById(@PathVariable("userId") Integer userId) {
        return userMapper.getUserById(userId);
    }
}



