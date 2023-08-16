package com.ashish.springdatajpa;

import com.ashish.model.Speciality;
import com.ashish.repository.SpecialityRepository;
import com.ashish.services.SpecialityService;

import java.util.HashSet;
import java.util.Set;

public class SpecialitySDJPAService implements SpecialityService {

    SpecialityRepository specialityRepository;

    public SpecialitySDJPAService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialities = new HashSet<>();
        specialityRepository.findAll().forEach(specialities::add);
        return specialities;
    }

    @Override
    public Speciality findById(Long aLong) {
        return specialityRepository.findById(aLong).orElse(null);
    }

    @Override
    public void delete(Speciality object) {
        specialityRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialityRepository.deleteById(aLong);
    }

    @Override
    public Speciality save(Speciality object) {
        return specialityRepository.save(object);
    }
}
