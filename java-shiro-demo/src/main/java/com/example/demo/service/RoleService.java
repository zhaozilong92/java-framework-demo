package com.example.demo.service;

import com.example.demo.dao.RoleDao;
import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IService<Role>{

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role findById(int id) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return roleDao.getRoleAll();
    }

    @Override
    public Role deleteById(int id) {
        return null;
    }

    @Override
    public List<Role> deleteByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Role add(Role role) {
        return null;
    }
}
