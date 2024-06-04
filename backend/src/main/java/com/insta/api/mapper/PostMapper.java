package com.insta.api.mapper;

import com.insta.api.model.DetailedPost;
import com.insta.api.model.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PostMapper {


    @Insert("INSERT INTO posts(post_identifier, image_url, user_id, post_date, post_location, " +
            "post_caption, post_alt, post_comments, post_likes, allow_comment, allow_like) " +
            "values(#{postIdentifier}, #{imageUrl}, #{userId}, #{postDate}, #{postLocation}, #{postCaption}," +
            " #{postAlt}, #{postComments}, #{postLikes}, #{allowComment}, #{allowLike})")
    int insertPost(String postIdentifier, String imageUrl, int userId, String postDate,
                   String postLocation, String postCaption, String postAlt,
                   int postComments, int postLikes, boolean allowComment, boolean allowLike);


    @Select("SELECT * FROM posts WHERE user_id = #{id}")
    List<Post> getPostsById(Integer id);

    @Select("SELECT * FROM posts WHERE post_id = #{postId}")
    DetailedPost getPostByPostId(Integer postId);

    @Delete("DELETE FROM posts WHERE post_id = #{postId}")
    Integer deletePost(Integer postId);

}
