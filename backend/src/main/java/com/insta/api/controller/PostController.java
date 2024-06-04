package com.insta.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.api.mapper.PostMapper;
import com.insta.api.mapper.UserMapper;
import com.insta.api.model.DetailedPost;
import com.insta.api.model.Post;
import com.insta.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PostController {

    protected static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    public User user;

    @Autowired
    public Post post;

    @Autowired
    public DetailedPost detailedPost;


    @GetMapping("/posts/user/{userId}")
    public Object getPostsById(@PathVariable("userId") Integer userId) {
        List<Post> allPosts = postMapper.getPostsById(userId);
        return allPosts;
    }


    @GetMapping("/posts/postid/{postId}")
    public Object getPostByPostId(@PathVariable("postId") Integer postId) {
        detailedPost = postMapper.getPostByPostId(postId);
        Integer userId = detailedPost.getUserId();
        user = userMapper.getUserById(userId);
        String userAvatar = user.getAvatar();
        String userName = user.getUserName();
        detailedPost.setUserAvatar(userAvatar);
        detailedPost.setUserName(userName);
        return detailedPost;
    }


    @PostMapping("/posts/new")
    public Integer newPost(@RequestBody String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        post = objectMapper.readValue(content, Post.class);
        logger.info(post.toString());
        Integer res = postMapper.insertPost(post.getPostIdentifier(), post.getImageUrl(), post.getUserId(), post.getPostDate(), post.getPostLocation(), post.getPostCaption(), post.getPostAlt(), post.getPostComments(), post.getPostLikes(), post.isAllowComment(), post.isAllowLike());
        return res;
    }


    @DeleteMapping("/posts/delete/{postId}")
    public Integer deletePost(@PathVariable("postId") Integer postId) {
        return postMapper.deletePost(postId);
    }


}
