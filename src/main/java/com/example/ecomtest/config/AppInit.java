package com.example.ecomtest.config;

import com.example.ecomtest.dao.AppUserRepository;
import com.example.ecomtest.dao.RoleRepository;
import com.example.ecomtest.entities.AppUser;
import com.example.ecomtest.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppInit implements CommandLineRunner {

    private final AppUserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AppInit(AppUserRepository repository) {
        this.repository = repository;
    }

    /**
     * initializing the DB in order to add an admin user for test purposes
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        if(roleRepository.count() == 0){
            roleRepository.saveAll(
                    List.of(
                            new Role("ROLE_ADMIN"),
                            new Role("ROLE_VENDEUR"),
                            new Role("ROLE_CLIENT")
                    ));
        }

        if(repository.count() == 0){
            repository.saveAll(
                    List.of(
                            new AppUser("appadmin","appadmin","admin@admin.app",
                                    passwordEncoder.encode("admin"),List.of(roleRepository.findRoleByName("ROLE_ADMIN"))),
                            new AppUser("vendeur","vendeur","vendeur@vendeur.app",
                                    passwordEncoder.encode("vendeur"),List.of(roleRepository.findRoleByName("ROLE_VENDEUR"))),
                            new AppUser("client","client","client@client.app",
                                    passwordEncoder.encode("client"),List.of(roleRepository.findRoleByName("ROLE_CLIENT")))
                    )

            );
        }
    }
}
