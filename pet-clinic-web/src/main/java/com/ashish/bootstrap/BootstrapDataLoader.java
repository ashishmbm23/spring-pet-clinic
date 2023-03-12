package com.ashish.bootstrap;

import com.ashish.model.Owner;
import com.ashish.model.PetType;
import com.ashish.model.Vet;
import com.ashish.services.OwnerService;
import com.ashish.services.PetTypeService;
import com.ashish.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapDataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public BootstrapDataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(101l);
        owner1.setFirstName("Ashish");
        owner1.setLastName("Verma");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(102l);
        owner2.setFirstName("Harshita");
        owner2.setLastName("Verma");
        ownerService.save(owner2);

        System.out.println("Loaded owners....");

        Vet vet1 = new Vet();
        vet1.setId(5001l);
        vet1.setFirstName("Rajkumar");
        vet1.setLastName("Hirani");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(5002l);
        vet2.setFirstName("Papa");
        vet2.setLastName("Pencho");
        vetService.save(vet2);

        System.out.println("Loaded vets....");

        PetType dog = new PetType();
        dog.setName("Dog");
        petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        petTypeService.save(cat);

        System.out.println("Loaded pet types");

    }
}
