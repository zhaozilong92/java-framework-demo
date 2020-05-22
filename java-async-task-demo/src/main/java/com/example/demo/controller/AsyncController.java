package com.example.demo.controller;

import com.example.demo.thread.AsyncThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncThread asyncThread;

    @RequestMapping("/start")
    public String start() throws ExecutionException, InterruptedException {
        Future<String> start = asyncThread.start();
        return start.get();
    }
}
