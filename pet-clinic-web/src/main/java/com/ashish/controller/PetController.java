package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.model.PetType;
import com.ashish.services.OwnerService;
import com.ashish.services.PetService;
import com.ashish.services.PetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owner/{ownerId}")
@RequiredArgsConstructor
public class PetController {

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
}
