package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
@RequiredArgsConstructor
public class OwnerIndexController {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/find"})
    public String findOwners(Model model){
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "owners/findOwners";
    }

    @GetMapping("")
    public String ownerList(Owner owner, BindingResult result, Model model)
    {
        if( owner.getLastName() == null ){
            owner.setLastName("");
        }

        List<Owner> ownerSet = ownerService.findAllByLastNameLike( '%' + owner.getLastName() + '%');

        if( ownerSet.size() == 0){
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }else{
            if( ownerSet.size() == 1){
                Owner selectedOwner = ownerSet.iterator().next();
                return "redirect:/owners/" + selectedOwner.getId() ;
            }else{
                model.addAttribute("selections", ownerSet);
                return "owners/ownersList";
            }
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable String ownerId){
        ModelAndView ownerMAV = new ModelAndView("owners/ownerDetails");
        ownerMAV.addObject( ownerService.findById( Long.parseLong(ownerId)));
        return ownerMAV;
    }

    @GetMapping("/new")
    public String createOwner(Model model){
        Owner owner = Owner.builder().build();
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwnerForm" ;
    }

    @PostMapping("/new")
    public String createOwner(@Valid Owner owner, BindingResult result){
        if( result.hasErrors() ){
            result.rejectValue("owner", "not valid", "not valid");
            return "owners/new";
        }
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String updateOwner(@PathVariable String ownerId, Model model){
        Owner owner = ownerService.findById(Long.parseLong(ownerId));
        if( owner == null ){
            return "redirect:/owners/new";
        }
        model.addAttribute("owner", owner);
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String updateOwner(@PathVariable String ownerId, @Valid Owner owner, BindingResult result){
        if( result.hasErrors() ){
            result.reject("not valid");
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        }
        owner.setId( Long.parseLong(ownerId));
        ownerService.save(owner);
        return "redirect:/owners/" + owner.getId();
    }
}
