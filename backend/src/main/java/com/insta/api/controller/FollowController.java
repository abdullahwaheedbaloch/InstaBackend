package com.insta.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.api.mapper.FollowMapper;
import com.insta.api.model.Follow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FollowController {

    protected static final Logger logger = LoggerFactory.getLogger(FollowController.class);
    @Autowired
    private FollowMapper followMapper;


    @Autowired
    public Follow follow;


    @GetMapping("/follows/followers/{userId}")
    public Object getFollowersById(@PathVariable("userId") Integer userId) {
        List<Follow> allFollowers = followMapper.getAllFollowers(userId);
        return allFollowers;
    }

    @GetMapping("/follows/followees/{userId}")
    public Object getFolloweesById(@PathVariable("userId") Integer userId) {
        logger.info(userId.toString());
        List<Follow> allFollowees = followMapper.getAllFollowees(userId);
        return allFollowees;
    }


    @PostMapping("/follows/follow")
    public Integer follow(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        follow = objectMapper.readValue(content, Follow.class);
        followMapper.insertFollow(follow.getFollowerId(), follow.getFolloweeId(), follow.getFollowTimestamp());
        return 1;
    }

    @PostMapping("/follows/unfollow")
    public Integer unfollow(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        follow = objectMapper.readValue(content, Follow.class);
        followMapper.deleteFollow(follow.getFollowerId(), follow.getFolloweeId());
        return 1;
    }


}
