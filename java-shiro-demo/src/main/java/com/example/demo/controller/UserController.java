package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public Result<User> getUser(@RequestParam("uid") int uid){
        User user = userService.findById(uid);
        return Result.success(user);
    }

    @GetMapping("/getAll")
    public Result<List<User>> getAllUser(){
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    @PostMapping("/add")
    public Result<User> addUser(@RequestBody User user) {
        User u = userService.add(user);
        return Result.success(u);
    }

    @GetMapping("/delete")
    public Result<User> deleteUser(@RequestParam("uid") int uid) {
        User user = userService.deleteById(uid);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result<?> updateUser(@RequestBody User user) {
        User u = userService.update(user);
        return Result.fail(Result.Code.FAIL_DEFAULT_500);
    }
}
