package com.example.ecomtest.controllers;


import com.example.ecomtest.dao.AppUserRepository;
import com.example.ecomtest.dao.RoleRepository;
import com.example.ecomtest.entities.AppUser;
import com.example.ecomtest.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RegistrationController {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "register", consumes = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody AppUser user) {
        try {
            List<Role> roleList = new ArrayList<>();
            user.getRoles().forEach(role -> {
                roleList.add(roleRepository.findRoleByName(role.getName()));
            });
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(roleList);
            repository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "user added successfully");
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An error occurred during signUp process");
        }
    }
}

