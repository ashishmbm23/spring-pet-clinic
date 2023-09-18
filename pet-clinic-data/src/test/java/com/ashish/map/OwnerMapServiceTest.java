package com.ashish.map;

import com.ashish.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    Long ownerId = 1L;
    String ownerLastName = "Verma";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(ownerLastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        assertNotNull(ownerSet);
        assertEquals(1, ownerSet.size());
    }

    @Test
    void save() {
        Long savedId = 2l;
        ownerMapService.save(Owner.builder().id(savedId).build());
        Owner savedOwner = ownerMapService.findById(savedId);
        assertNotNull(savedOwner);
        assertEquals(savedId, savedOwner.getId());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        Owner deletedOwner = ownerMapService.findById(ownerId);
        assertNull(deletedOwner);
    }

    @Test
    void delete() {
        Owner toDeleteOwner = ownerMapService.findById(ownerId);
        ownerMapService.delete(toDeleteOwner);
        Owner deletedOwner = ownerMapService.findById(ownerId);
        assertNull(deletedOwner);
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertNotNull(owner);
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(ownerLastName);
        assertNotNull(owner);
    }

    @Test
    void ifLastNameOwnerNotPresent(){
        Owner owner = ownerMapService.findByLastName("someRandomName");
        assertNull(owner);
    }
}