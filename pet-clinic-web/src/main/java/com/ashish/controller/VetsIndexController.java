package com.ashish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetsIndexController {

    @RequestMapping({"/vets", "/vets/index", "/vets/index.html"})
    public String getListOfVets(){
        return "vets/index";
    }
}
