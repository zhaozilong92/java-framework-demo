package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private Date ctime;
    private String salt;
    private String desc;

    private List<Role> roles;
}
