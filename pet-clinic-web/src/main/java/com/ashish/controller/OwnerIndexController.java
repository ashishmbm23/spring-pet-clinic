package com.ashish.controller;

import com.ashish.model.Owner;
import com.ashish.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
@RequiredArgsConstructor
public class OwnerIndexController {

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
        }else{
            if( ownerSet.size() == 1){
                Owner selectedOwner = ownerSet.iterator().next();
                return "redirect:/owners/" + selectedOwner.getId() ;
            }else{
                model.addAttribute("selections", ownerSet);
                return "owners/ownersList";
            }
        }
        return "owners/ownersList";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable String ownerId){
        ModelAndView ownerMAV = new ModelAndView("owners/ownerDetails");
        ownerMAV.addObject( ownerService.findById( Long.parseLong(ownerId)));
        return ownerMAV;
    }
}
