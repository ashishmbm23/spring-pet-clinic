package com.ashish.springdatajpa;

import com.ashish.model.Owner;
import com.ashish.repository.OwnerRepository;
import com.ashish.repository.PetRepository;
import com.ashish.repository.PetTypeRepository;
import com.ashish.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
@Service
@Profile("spring.service.jpa")
public class OwnerSDJpaService implements OwnerService {

    private final OwnerRepository ownerRepository ;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;

    public OwnerSDJpaService(OwnerRepository ownerRepository, PetRepository petRepository, PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<Owner> findAll() {
        Iterable<Owner> owners = ownerRepository.findAll();
        Set<Owner> ownerSet = new HashSet<>();
        owners.forEach(ownerSet::add);
        return ownerSet;
    }

    @Override
    public Owner findById(Long aLong) {
        Optional<Owner> ownerOptional = ownerRepository.findById(aLong);
        return ownerOptional.orElse(null);
    }

    @Override
    public void delete(Owner object) {
        ownerRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        ownerRepository.findById( aLong );
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepository.save(object);
    }

    @Override
    public Owner findByLastName(String name) {
        return ownerRepository.findByLastName( name );
    }
}
