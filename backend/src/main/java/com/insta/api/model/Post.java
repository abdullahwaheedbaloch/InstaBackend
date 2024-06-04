package com.insta.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Post {
    private int postId;
    private String postIdentifier;
    private String imageUrl;
    private int userId;
    private String postDate;
    private String postLocation;
    private String postCaption;
    private String postAlt;
    private int postComments;
    private int postLikes;
    private boolean allowComment;
    private boolean allowLike;

}
