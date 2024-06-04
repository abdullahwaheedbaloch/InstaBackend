package com.insta.api.mapper;

import com.insta.api.model.Follow;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FollowMapper {


    @Insert("INSERT INTO follows(follower_id, followee_id, follow_timestamp) values(#{followerId}, #{followeeId}, #{followTimestamp})")
    int insertFollow(Integer followerId, Integer followeeId, String followTimestamp);

    @Delete("DELETE FROM follows WHERE follower_id = #{followerId} and followee_id = #{followeeId}")
    int deleteFollow(Integer followerId, Integer followeeId);

    @Select("SELECT * FROM follows WHERE followee_id = #{userId} ")
    List<Follow> getAllFollowers(Integer userId);

    @Select("SELECT * FROM follows WHERE follower_id = #{id} ")
    List<Follow> getAllFollowees(Integer id);
}


