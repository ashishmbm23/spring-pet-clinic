package com.ashish.controller;

import com.ashish.model.Vet;
import com.ashish.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;


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

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getListOfVets(){
        return vetService.findAll();
    }
}
