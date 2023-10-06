package com.ashish.map;

import com.ashish.model.Owner;
import com.ashish.model.Pet;
import com.ashish.services.OwnerService;
import com.ashish.services.PetService;
import com.ashish.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner save(Owner object) {
        if(object != null){
            if(object.getPets() != null){
                object.getPets().forEach(pet -> {
                    if(pet.getPetType() != null){
                        if( pet.getPetType().getId() == null ){
                            pet.setPetType( petTypeService.save(pet.getPetType()));
                        }

                        if(pet.getId() == null){
                            Pet savedPet = petService.save(pet);
                            pet.setId(savedPet.getId());
                        }
                    }else{
                        throw new RuntimeException("Pet type is required");
                    }
                });
            }

            return super.save(object);
        }else {
            return null;
        }

    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        Set<Owner> ownerSet = super.findAll();

        return ownerSet.stream()
                .filter(owner -> owner.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String name) {
        Set<Owner> ownerSet = super.findAll();

        return ownerSet.stream()
                .filter( owner -> owner.getLastName().contains( name ))
                .collect( Collectors.toList());
    }
}
