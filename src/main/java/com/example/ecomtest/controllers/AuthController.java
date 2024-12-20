package com.example.ecomtest.controllers;

import com.example.ecomtest.dao.AppUserRepository;
import com.example.ecomtest.entities.AppUser;
import com.example.ecomtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/req/login")
    public ResponseEntity<Object> login(@RequestBody AppUser user){
        try {
             authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("roles", service.loadUserByUsername(user.getEmail()).getAuthorities().toString());
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }


    // the following endpoints where used for the initial solution
    // without angular and using thymeleaf (see first commit)

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
