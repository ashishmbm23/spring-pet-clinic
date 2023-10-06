package com.ashish.controller;

import com.ashish.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners")
@Controller
@RequiredArgsConstructor
public class OwnerIndexController {

    private final OwnerService ownerService;

    @RequestMapping({"","/", "/index", "/index.html"})
    public String getListOfOwners(Model model){
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping({"/find"})
    public String findOwners(){
        return "notImplemented";
    }

    @GetMapping("/{ownerId}/show")
    public ModelAndView showOwner(@PathVariable String ownerId){
        ModelAndView ownerMAV = new ModelAndView("owners/ownerDetails");
        ownerMAV.addObject( ownerService.findById( Long.parseLong(ownerId)));
        return ownerMAV;
    }
}
