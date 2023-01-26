package com.ashish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerIndexController {

    @RequestMapping({"","/", "/index", "/index.html"})
    public String getListOfOwners(){
        return "owners/index";
    }
}
