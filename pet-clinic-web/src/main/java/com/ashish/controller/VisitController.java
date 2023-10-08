package com.ashish.controller;

import com.ashish.model.Pet;
import com.ashish.model.Visit;
import com.ashish.services.PetService;
import com.ashish.services.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    @InitBinder
    public void setInitBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable String petId, Model model){
        Pet pet = petService.findById( Long.parseLong( petId) );
        Visit visit = new Visit();
        pet.getVisits().add( visit );
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable String petId, Model model){
        return "pets/createOrUpdateVisitForm" ;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initProcessNewVisitForm(@Valid Visit visit, BindingResult result){
        if( result.hasErrors() ){
            return "pets/createOrUpdateVisitForm";
        }else{
            visitService.save(visit);
            return "redirect:/owners/" + visit.getPet().getOwner().getId();
        }
    }

}
