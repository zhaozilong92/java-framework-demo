package com.example.demo.dao;

import com.example.demo.domian.TrainTickets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TicketsDao {

    @Select("select * from tbl_train_tickets")
    List<TrainTickets> findAll();

    @Select("update tbl_train_tickets set count = #{count}")
    void buy(int count);
}
