package com.ashish.controller;

import com.ashish.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class VetsIndexController {

    private final VetService vetService;

    public VetsIndexController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets/index", "/vets/index.html", "/vets.html"})
    public String getListOfVets(Model model)
    {
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }
}
