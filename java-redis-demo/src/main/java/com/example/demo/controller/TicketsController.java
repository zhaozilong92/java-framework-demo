package com.example.demo.controller;

import com.example.demo.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    @GetMapping("/balance")
    public int getBalance(){
        return ticketsService.getBalance();
    }

    @GetMapping("/buy")
    public boolean buy() throws InterruptedException {
        for(int i=1;i<=100; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (ticketsService.buy()) {
//                        System.out.println("窗口 " + Thread.currentThread().getName() + " 买到票");
                    }
                }
            });
            t.setName("窗口: " + i);
            t.start();
        }
        return true;
    }

}
