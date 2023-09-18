package com.ashish.springdatajpa;

import com.ashish.model.Owner;
import com.ashish.repository.OwnerRepository;
import com.ashish.repository.PetRepository;
import com.ashish.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Verma";
    public static final long ID = 1L;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        Owner owner2 = Owner.builder().id(2L).build();
        ownerSet.add(owner);
        ownerSet.add(owner2);
        when(ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> resultOwnerSet = ownerSDJpaService.findAll();
        assertNotNull(resultOwnerSet);
        assertEquals( resultOwnerSet.size(), 2);
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(ID)).thenReturn(Optional.of(owner));
        Owner resultOwner = ownerSDJpaService.findById(ID);
        assertNotNull(resultOwner);
        assertEquals(resultOwner.getId(), ID);
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(owner);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(ID);
        verify( ownerRepository ).deleteById(anyLong());
    }

    @Test
    void save() {
        Owner owner2 = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(owner2);
        Owner savedOwner = ownerSDJpaService.save(owner2);
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void findByLastName() {
        Mockito.when(ownerRepository.findByLastName(Mockito.any())).thenReturn(owner);
        Owner smith = ownerSDJpaService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, smith.getLastName());
        Mockito.verify(ownerRepository).findByLastName(Mockito.any());
    }
}