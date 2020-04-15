package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.RealmService;
import com.example.demo.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/realm")
public class RealmController {

    @Autowired
    private RealmService realmService;

    @PostMapping("/login")
    public Result<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            // shiro中进行登录
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            currentUser.login(token);
            User u = realmService.getUserIdByNameAndPassword(username, password);
            return Result.success(u);
        } catch (Exception e) {
            return Result.fail();
        }
    }

    @GetMapping("/logout")
    public boolean logout(@RequestParam("uid") String uid) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return true;
    }

    @GetMapping("/noPermission")
    public Result<?> noPermission(){
        Subject currentUser = SecurityUtils.getSubject();
        String username = (String)currentUser.getPrincipal();
        return Result.fail(Result.Code.NO_PERMISSION_302, String.format("current username: [%s] is no permission", username));
    }
}
