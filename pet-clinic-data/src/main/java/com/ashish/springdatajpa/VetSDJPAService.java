package com.ashish.springdatajpa;

import com.ashish.model.Vet;
import com.ashish.repository.VetRepository;
import com.ashish.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("spring.service.jpa")
public class VetSDJPAService implements VetService {

    private final VetRepository vetRepository ;

    public VetSDJPAService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        Iterable<Vet> vetIterable = vetRepository.findAll();
        vetIterable.forEach( vets :: add );
        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
        return vetRepository.findById(aLong).orElse(null) ;
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById( aLong );
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }
}
