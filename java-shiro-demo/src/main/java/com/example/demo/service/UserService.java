package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserRoleDao;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService implements IService<User>{

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public User findById(int id) {
        List<User> users = userDao.getUserById(id);
        if(null == users || users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> findAll() {
        return userDao.getAllUser();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public User deleteById(int id) {
        String message = "";
        List<User> userById = userDao.getUserById(id);
        if(null == userById || userById.isEmpty()){
            message = "需要删除的用户不存在";
            log.error(message);
            return null;
        }
        // 删除权限关联
        boolean result = true;
        result &= userRoleDao.deleteUserAndRole(id);
        // 删除用户
        result &= userDao.deleteUserById(id);

        message = "删除用户{} uid: {}";
        if(!result){
           log.error(message, "成功", id);
        }else {
            log.error(message, "失败", id);
            throw new RuntimeException();
        }

        return userById.get(0);
    }

    @Override
    public List<User> deleteByIds(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        ids.forEach(id -> {
            User user = deleteById(id);
            users.add(user);
        });
        return users;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    @Override
    public User add(User user) {
        String message = "";
        boolean result = true;
        // 添加用户到user表
        user.setCtime(new Date());
        user.setSalt(null);
        result &= userDao.addUser(user);
        int uid = userDao.getUserIdByName(user.getUsername());

        // 关联用户角色
        List<Role> roles = user.getRoles();
        roles.forEach(role -> {

            if(userRoleDao.addUserIdAndRoleId(uid, role.getId())){
                log.info("关联用户{} --> 权限{} {}", uid, role.getId(), "成功");
            }else {
                log.error("关联用户{} --> 权限{} {}", uid, role.getId(), "失败");
                throw new RuntimeException();
            }
        });

        List<User> us = userDao.getUserById(uid);
        if(!result || null == us || us.isEmpty()){
            return null;
        }
        return us.get(0);
    }
}
