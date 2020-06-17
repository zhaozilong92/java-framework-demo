package com.example.demo.service;

import com.example.demo.config.RedisConfig;
import com.example.demo.domian.TrainTickets;
import com.example.demo.dao.TicketsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TicketsDao ticketsDao;

    public int getBalance() {
        int count = 0;
        // 先从缓存中取
        Object trainTicketsObj = redisTemplate.opsForValue().get("train_tickets:zhanpiao");
        // 若没有则从数据库查询
        if (null == trainTicketsObj) {
            TrainTickets trainTickets = ticketsDao.findAll().get(0);
            count = trainTickets.getCount();
            redisTemplate.opsForValue().set("train_tickets:zhanpiao", count);
        } else {
            count = (int) trainTicketsObj;
        }

        redisTemplate.convertAndSend(RedisConfig.Channel.A.toString(), "获取到count: " + count);

        return count;
    }

    public boolean buy() {

        Long decrement = redisTemplate.opsForValue().decrement("train_tickets:zhanpiao");
        if (0 > decrement) {
            redisTemplate.opsForValue().set("train_tickets:zhanpiao", 0);
            print("失败, 没有东西卖了...");
            return false;
        }
        if (0 == decrement) {
            redisTemplate.opsForValue().set("train_tickets:zhanpiao", 0);
            print("最后一张票 已卖完~~~");
            return true;
        }

        print("买票成功!!!");
        return true;


//        Object zhanpiao = redisTemplate.opsForValue().get("train_tickets:zhanpiao");
//        if (null == zhanpiao) {
//            System.out.println("票卖完了: 窗口" + Thread.currentThread().getName());
//            return false;
//        }
//
//        if(0 >= (int)zhanpiao){
//            redisTemplate.delete("train_tickets:zhanpiao");
//            System.out.println("当前票数为: " + zhanpiao + " 票卖完了: 窗口" + Thread.currentThread().getName());
//            return false;
//        }
//
//        try {
//            redisTemplate.watch("train_tickets:zhanpiao");
//            redisTemplate.multi();
//            redisTemplate.opsForValue().decrement("train_tickets:zhanpiao");
//            redisTemplate.exec();
//            System.out.println("成功买到票: 窗口" + Thread.currentThread().getName());
//            return true;
//        } catch (Exception e) {
//            System.out.println("买票失败: " + e.getMessage() + " 窗口" + Thread.currentThread().getName());
//            redisTemplate.discard();
//            return false;
//        }finally {
//            redisTemplate.unwatch();
//        }
    }


    private void print(String... message) {
        String info = "当前: " + Thread.currentThread().getName();
        for (String s : message) {
            info += ", " + s;
        }
        System.out.println(info);
    }
}
