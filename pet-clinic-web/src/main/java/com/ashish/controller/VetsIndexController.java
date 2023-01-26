package com.ashish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vets")
@Controller
public class VetsIndexController {

    @RequestMapping({"","/", "/index", "/index.html"})
    public String getListOfVets(){
        return "vets/index";
    }
}
