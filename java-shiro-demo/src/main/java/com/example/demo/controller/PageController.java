package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping({"/error", "/error.html"})
    public String error(){
        return "error";
    }

    @RequestMapping({"/user", "/user.html"})
    public String user(){
        return "user";
    }
}
