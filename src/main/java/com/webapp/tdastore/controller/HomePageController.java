package com.webapp.tdastore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomePageController {
    @GetMapping
    public String getWelcomePage() {
        return "index";
    }
}
