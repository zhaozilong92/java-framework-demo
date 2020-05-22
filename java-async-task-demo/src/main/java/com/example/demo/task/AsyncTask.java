package com.example.demo.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling // 1.开启定时任务
public class AsyncTask {

    private int i = 0;

    /**默认是fixedDelay 上一次执行完毕时间后执行下一轮*/
    @Scheduled(cron = "0/5 * * * * *") // 2.使用注解表明如何执行任务
    @Async
    public void run() throws InterruptedException {
        Thread.sleep(6000);
        i++;
        System.out.println(String.format("%s=====>>>>> %s 使用cron  %s", i, Thread.currentThread().getName(), System.currentTimeMillis()/1000));
    }

    /**fixedRate:上一次开始执行时间点之后5秒再执行*/
    @Scheduled(fixedRate = 5000)
    @Async
    public void run1() throws InterruptedException {
        Thread.sleep(6000);
        i++;
        System.out.println(String.format("%s=====>>>>> %s 使用fixedRate  %s", i, Thread.currentThread().getName(), System.currentTimeMillis()/1000));
    }

    /**fixedDelay:上一次执行完毕时间点之后5秒再执行*/
    @Scheduled(fixedDelay = 5000)
    @Async
    public void run2() throws InterruptedException {
        Thread.sleep(7000);
        i++;
        System.out.println(String.format("%s=====>>>>> %s 使用fixedDelay  %s", i, Thread.currentThread().getName(), System.currentTimeMillis()/1000));
    }

    /**第一次延迟2秒后执行，之后按fixedDelay的规则每5秒执行一次*/
    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    @Async
    public void run3(){
        i++;
        System.out.println(String.format("%s=====>>>>> %s 使用initialDelay  %s", i, Thread.currentThread().getName(), System.currentTimeMillis()/1000));
    }

}
