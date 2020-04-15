package com.example.demo.service;

import java.util.List;

public interface IService<T> {

    List<T> findAll();

    T findById(int id);

    T add(T t);

    T deleteById(int id);

    List<T> deleteByIds(List<Integer> ids);

    T update(T t);
}
