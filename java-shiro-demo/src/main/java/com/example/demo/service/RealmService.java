package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RealmService implements IService<User>{

    @Autowired
    private UserDao userDao;

    public User getUserIdByNameAndPassword(String name, String password) {
        Integer uid = userDao.getUserIdByNameAndPassword(name, password);
        if(null == uid){
            return null;
        }
        List<User> users = userDao.getUserById(uid);
        if(null == users || users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    public User getUserByName(String name) {
        int uid = userDao.getUserIdByName(name);
        List<User> users = userDao.getUserById(uid);
        if(null == users || users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findById(int id) {
        List<User> users = userDao.getUserById(id);
        log.info("获取到用户信息为:{}", users);
        if(null == users || users.isEmpty()){
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User deleteById(int id) {
        return null;
    }

    @Override
    public List<User> deleteByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User add(User user) {
        return null;
    }
}
