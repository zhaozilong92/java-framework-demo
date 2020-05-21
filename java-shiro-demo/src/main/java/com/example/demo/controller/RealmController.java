package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.RealmService;
import com.example.demo.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/realm")
public class RealmController {

    @Autowired
    private RealmService realmService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        try {
            // shiro中进行登录
            Subject currentUser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            currentUser.login(token);
            User u = realmService.getUserIdByNameAndPassword(user.getUsername(), user.getPassword());
            return Result.success(u);
        } catch (Exception e) {
            return Result.fail();
        }
    }

    /**
     * 不需要实现 配置的默认logout过滤器就可以实现退出
     * @return
     */
    /*@GetMapping("/logout")
    public boolean logout(@RequestParam("uid") String uid) {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return true;
    }**/

    @GetMapping("/noPermission")
    public Result<?> noPermission(){
        Subject currentUser = SecurityUtils.getSubject();
        User user = (User)currentUser.getPrincipal();
        Set<String> permissions = new HashSet<>();
        user.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> {
                permissions.add(permission.getName());
            });
        });
        return Result.fail(Result.Code.NO_PERMISSION_302, String.format("Current username: %s, permissions: %s. Is no permission", user.getUsername(), permissions));
    }
}
