package com.ashish.services;

import com.ashish.model.Owner;

public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String name);
}
