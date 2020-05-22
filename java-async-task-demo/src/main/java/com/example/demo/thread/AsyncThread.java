package com.example.demo.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncThread {


    @Async("taskExecutor") // 2.使用注解表明这是一个多线程任务
    public Future<String> start() {
        System.out.println(Thread.currentThread().getName() + ": 线程开启");
        return new AsyncResult<>( "线程开启");
    }
}
