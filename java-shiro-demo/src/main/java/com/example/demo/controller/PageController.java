package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问对应的路径跳转到/templates/下页面
 */
@Controller
public class PageController {

    @RequestMapping({"/", "/index", "index.html"})
    public String index(){
        return "index";
    }

    @RequestMapping({"/login", "/login.html"})
    public String login(){
        return "login";
    }

    @RequestMapping({"/noauth", "/noauth.html"})
    public String error(){
        return "noauth";
    }

    @RequestMapping({"/user", "/user.html"})
    public String user(){
        return "user";
    }
}
