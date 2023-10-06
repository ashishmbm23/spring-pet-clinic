package com.ashish.repository;

import com.ashish.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Owner findByLastName( String name );
    
    List<Owner> findAllByLastNameLike(String name);

}
