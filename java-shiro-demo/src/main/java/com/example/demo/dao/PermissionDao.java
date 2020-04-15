package com.example.demo.dao;

import com.example.demo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionDao {

    @Select("SELECT * FROM permission WHERE id IN(SELECT pid FROM role_permission WHERE rid=#{rid})")
    List<Permission> getPermissionByRoleId(int rid);

    @Select("SELECT * FROM permission")
    List<Permission> getPermissionAll();
}
