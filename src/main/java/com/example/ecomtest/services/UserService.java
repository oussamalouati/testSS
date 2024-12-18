package com.example.ecomtest.services;

import com.example.ecomtest.dao.AppUserRepository;
import com.example.ecomtest.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> user = repository.findByEmail(email);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder().username(userObj.getEmail())
                    .password(userObj.getPassword()).authorities(getAuthorities(userObj)).build();
        }else {
            throw new UsernameNotFoundException(email);
        }
    }
    private Collection<? extends GrantedAuthority> getAuthorities(AppUser user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
