package com.example.demo.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncThread {


    @Async // 2.使用注解表明这是一个多线程任务
    public void start() {
        System.out.println(Thread.currentThread().getName() + ": 线程开启");
    }
}
