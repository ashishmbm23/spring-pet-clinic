package com.ashish.map;

import com.ashish.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity,ID extends Long> {
    protected Map<Long,T> map = new HashMap<>();

    Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    T save( T object){
        if(object != null){
            if(object.getId() == null){
                object.setId(getNextId());
            }
            map.put(object.getId(), object);
        }else{
            throw new RuntimeException("Object not found");
        }

        return object;
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void delete(T obj){
        map.entrySet().removeIf( entry -> entry.getValue().equals(obj));
    }

    Long getNextId(){
        Long nextId;
        try{
         nextId = (Collections.max(map.keySet())) + 1 ;
        }catch (Exception e){
            return 1l;
        }
        return nextId;
    }
}
