package com.produtos.grupo6.spring.aws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String viewHomePage() {
        return "upload";
    }
}
