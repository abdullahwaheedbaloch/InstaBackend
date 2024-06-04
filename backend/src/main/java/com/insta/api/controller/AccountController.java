package com.insta.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.api.mapper.UserMapper;
import com.insta.api.model.User;
import com.insta.api.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class AccountController {

    protected static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public User user;


    @PostMapping("/accounts/signup")
    public String signup(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(content, User.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String token = JWTUtil.sign(user.getUserName(), user.getUserId());
        userMapper.insertUser(user.getUserName(), user.getEmail(), user.getPassword(), user.getFullName(), user.getAvatar());
        return token;
    }


    @PostMapping("/accounts/update")
    public Integer userUpdate(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(content, User.class);
        Integer res = userMapper.updateInSettings(user.getUserName(), user.getAvatar(), user.getFullName(), user.getWebsite(), user.getBio(), user.getPhoneNumber());
        return res;
    }

    @PostMapping("/accounts/login")
    public ResponseEntity<String> login(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User inputUser = objectMapper.readValue(content, User.class);
        String emailOrUsername = inputUser.getEmail();
        String rawPassword = inputUser.getPassword();

        // Get all users only once
        List<User> allUsers = userMapper.getAllUsers();
        User foundUser = null;

        // Find the user by username or email
        for (User user : allUsers) {
            if (user.getUserName().equals(emailOrUsername) || user.getEmail().equals(emailOrUsername)) {
                foundUser = user;
                break;
            }
        }

        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NO_SUCH_ACCOUNT");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(rawPassword, foundUser.getPassword());

        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG_PASSWORD");
        }

        // Update last login time (assuming `userMapper.updateLoginTime` handles null or invalid input properly)
        String currentTime = LocalDateTime.now().toString();
        userMapper.updateLoginTime(currentTime, foundUser.getUserName());

        // Generate token
        String token = JWTUtil.sign(foundUser.getUserName(), foundUser.getUserId());

        return ResponseEntity.ok(token);
    }
}
