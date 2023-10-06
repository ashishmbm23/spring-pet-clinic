package com.ashish.bootstrap;

import com.ashish.model.*;
import com.ashish.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BootstrapDataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService  visitService;

    public BootstrapDataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                               SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if(count == 0)
            loadData();
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCat = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Owner owner1 = new Owner();
        owner1.setId(101l);
        owner1.setFirstName("Ashish");
        owner1.setLastName("Verma");
        owner1.setAddress("711313");
        owner1.setCity("Jaipur");
        owner1.setTelephone("1234413131");

        Pet ashishspet = new Pet();
        ashishspet.setName("Rosco");
        ashishspet.setPetType(savedCat);
        ashishspet.setBirthDate(LocalDate.now());
        ashishspet.setOwner(owner1);
        owner1.getPets().add(ashishspet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(102l);
        owner2.setFirstName("Harshita");
        owner2.setLastName("Verma");
        owner2.setAddress("711313");
        owner2.setCity("Jaipur");
        owner2.setTelephone("1234413131");

        Pet harshitaspet = new Pet();
        harshitaspet.setName("Jimmy");
        harshitaspet.setPetType(savedDog);
        harshitaspet.setBirthDate(LocalDate.now());
        harshitaspet.setOwner(owner2);


        Visit jimmyVisit = new Visit();
        jimmyVisit.setPet(harshitaspet);
        jimmyVisit.setDate(LocalDate.now());
        jimmyVisit.setDescription("loss of hair");

        harshitaspet.getVisits().add(jimmyVisit);
        owner2.getPets().add(harshitaspet);
        ownerService.save(owner2);
        System.out.println("Loaded owners....");



        System.out.println("Loaded visits....");
        Vet vet1 = new Vet();
        vet1.setId(5001l);
        vet1.setFirstName("Rajkumar");
        vet1.setLastName("Hirani");
        vet1.getSpecialities().add(savedDentistry);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(5002l);
        vet2.setFirstName("Papa");
        vet2.setLastName("Pencho");
        vet2.getSpecialities().add(savedSurgery);
        vetService.save(vet2);

        System.out.println("Loaded vets....");
    }
}
