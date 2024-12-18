package com.example.ecomtest.config;

import com.example.ecomtest.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return userService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm-> {
            httpForm.loginPage("/req/login").permitAll();
            httpForm.defaultSuccessUrl("/index");
        })
                .authorizeHttpRequests(registry->
                {
                    registry.requestMatchers("/css/**", "/req/signup/**")
                        .permitAll();
                    registry.requestMatchers("/admin").hasRole("ADMIN");
                    registry.requestMatchers("/client").hasAnyRole("CLIENT","ADMIN");
                    registry.requestMatchers("/vendeur").hasAnyRole("VENDEUR","ADMIN");
                    registry.anyRequest().authenticated();
                })
                .logout((logout)-> logout.logoutRequestMatcher(new RequestMatcher() {
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return request.getRequestURI().equals("/logout");
                    }
                }).logoutSuccessUrl("/req/login")).build();
    }
}
