package com.example.demo.service;

import com.example.demo.dao.PermissionDao;
import com.example.demo.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService implements IService<Permission>{

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Permission findById(int id) {
        return null;
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.getPermissionAll();
    }

    @Override
    public Permission deleteById(int id) {
        return null;
    }

    @Override
    public List<Permission> deleteByIds(List<Integer> ids) {
        List<Permission> permissions = new ArrayList<>();
        ids.forEach(id -> {
            Permission permission = deleteById(id);
            permissions.add(permission);

        });
        return permissions;
    }

    @Override
    public Permission update(Permission permission) {
        return null;
    }

    @Override
    public Permission add(Permission permission) {
        return null;
    }

}
