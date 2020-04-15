package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getAll")
    public Result<List<Role>> getRoleAll(){
        List<Role> roles = roleService.findAll();
        return Result.success(roles);
    }
}
