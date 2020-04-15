package com.example.demo.dao;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("SELECT * FROM role WHERE id IN(SELECT rid FROM user_role WHERE uid=#{uid})")
    @Results(value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "permissions", column = "id",
                    many = @Many(select = "com.example.demo.dao.PermissionDao.getPermissionByRoleId", fetchType = FetchType.DEFAULT))
    })
    List<Role> getRoleByUserId(int uid);

    @Select("SELECT * FROM role")
    List<Role> getRoleAll();
}
