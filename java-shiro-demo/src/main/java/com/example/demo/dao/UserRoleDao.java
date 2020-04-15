package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDao {

    @Insert("INSERT INTO user_role VALUES(null, #{uid}, #{rid})")
    boolean addUserIdAndRoleId(int uid, int rid);

    @Delete("DELETE FROM user_role WHERE uid = #{uid}")
    boolean deleteUserAndRole(int uid);
}
