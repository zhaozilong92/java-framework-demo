package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM `user` WHERE `user`.id = #{id}")
    @Results(value = {
            @Result(id=true, property = "id", column = "id"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "com.example.demo.dao.RoleDao.getRoleByUserId", fetchType = FetchType.DEFAULT))
    })
    List<User> getUserById(int id);

    @Select("SELECT * FROM `user`")
    @Results(value = {
            @Result(id=true, property = "id", column = "id"),
            @Result(property = "roles", column = "id",
                    many = @Many(select = "com.example.demo.dao.RoleDao.getRoleByUserId", fetchType = FetchType.DEFAULT))
    })
    List<User> getAllUser();

    @Insert("INSERT INTO `user` VALUES(#{user.id}, #{user.username}, #{user.password}, #{user.ctime}, #{user.salt}, #{user.desc})")
    Boolean addUser(@Param("user") User user);

    @Delete("DELETE FROM `user` WHERE id = #{uid}")
    Boolean deleteUserById(int uid);

    @Select("SELECT id FROM `user` WHERE `user`.username = #{username}")
    Integer getUserIdByName(String username);

    @Select("SELECT id FROM `user` WHERE `user`.username = #{username} AND `user`.password = #{password}")
    Integer getUserIdByNameAndPassword(String username, String password);
}
