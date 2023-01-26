package com.ashish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OwnerIndexController {

    @RequestMapping({"/owners", "/owners/index", "/owners/index.html"})
    public String getListOfOwners(){
        return "owners/index";
    }
}
