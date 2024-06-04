package com.insta.api.mapper;
import com.insta.api.model.MutualFriend;
import com.insta.api.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {


    @Insert("INSERT INTO users(user_name, email, password, full_name, avatar) values(#{userName}, #{email}, #{password}, #{fullName}, #{avatar})")
    int insertUser(String userName, String email, String password, String fullName, String avatar);

    @Select("SELECT * FROM users WHERE user_id = #{id}")
    User getUserById(Integer id);

    @Select("SELECT * FROM users WHERE user_name = #{name}")
    User getUserByName(String name);

    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Update("UPDATE users SET last_login = #{loginTime} WHERE user_name = #{userName}")
    int updateLoginTime(String loginTime, String userName);

    @Update("UPDATE users SET avatar = #{imageUrl}, full_name = #{fullName}, website = #{website}, bio = #{bio}, phone_number = #{phoneNumber} " +
            "WHERE user_name = #{userName}")
    int updateInSettings(String userName, String imageUrl, String fullName, String website, String bio, String phoneNumber);
}