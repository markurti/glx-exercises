package com.logistics.repository;

import java.util.List;

public interface IRepository<T> {
    T save(T entity);
    T findById(Long id);
    List<T> findAll();
    void update(T entity);
    void delete(Long id);
}
