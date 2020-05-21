package com.example.demo.controller;

import com.example.demo.task.AsyncTask;
import com.example.demo.thread.AsyncThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncThread asyncThread;

    @RequestMapping("/start")
    public String start(){
        asyncThread.start();
        return "线程开启";
    }
}
