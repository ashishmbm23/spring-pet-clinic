package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.model.Pet;
import com.ashish.model.PetType;
import com.ashish.services.OwnerService;
import com.ashish.services.PetService;
import com.ashish.services.PetTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
@RequiredArgsConstructor
public class PetController {

    public static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";
    public static final String REDIRECT_OWNERS = "redirect:/owners/";
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final PetService petService;

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable String ownerId){
        return ownerService.findById( Long.parseLong(ownerId));
    }

    @ModelAttribute("types")
    public Collection<PetType> findPetTypes(){
        Collection<PetType> petTypes = petTypeService.findAll();
        return petTypes;
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String createPet(Owner owner, Model model){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute( "pet", pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/pets/new")
    public String createPet(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model){
        if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet( pet.getName(), true) != null ){
            result.rejectValue("name", "duplicate", "already Present");
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        if( result.hasErrors() ){
            model.put("pet", pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        }
        petService.save(pet);
        return REDIRECT_OWNERS + owner.getId() ;
    }

    @GetMapping("/pets/{petId}/edit")
    @Transactional
    public String updatePet(@PathVariable String petId, Owner owner, Model model){
        Pet pet = petService.findById(Long.parseLong(petId));
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    @Transactional
    public String updatePet(Owner owner, @Valid Pet pet, BindingResult result, Model model){
        owner.getPets().add(pet);
        if( result.hasErrors() ){
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        petService.save( pet );
        return REDIRECT_OWNERS + owner.getId();
    }
}
