package com.ashish.services;

import java.util.Set;

public interface CrudService<T,ID> {
    Set<T> findAll();

    T findById(ID id);

    void delete(T object);

    void deleteById(ID id);

    T save(T object);
}
