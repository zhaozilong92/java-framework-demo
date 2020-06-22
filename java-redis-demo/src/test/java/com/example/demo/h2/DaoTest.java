package com.example.demo.h2;

import com.example.demo.dao.TicketsDao;
import com.example.demo.domian.TrainTickets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DaoTest {

    @Autowired
    private TicketsDao ticketsDao;

    @Test
    public void findAll(){
        List<TrainTickets> all = ticketsDao.findAll();
        System.out.println(all);
    }

    @Test
    public void buy(){

    }
}
