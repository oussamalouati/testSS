package com.example.ecomtest.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/req/login")
    public String login(){
        return "signin";
    }

    @GetMapping("/req/signup")
    public String signUp(){
        return "signup";
    }

    @GetMapping("/index")
    public String welcome(){
        return "index";
    }

    @GetMapping("/vendeur")
    public String welcomeVendeur(){
        return "vendeur";
    }

    @GetMapping("/client")
    public String welcomeClient(){
        return "client";
    }

    @GetMapping("/admin")
    public String welcomeAdmin(){
        return "admin";
    }


}
