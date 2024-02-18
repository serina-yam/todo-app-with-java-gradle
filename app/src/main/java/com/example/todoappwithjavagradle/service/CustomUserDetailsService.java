package com.example.todoappwithjavagradle.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.todoappwithjavagradle.entity.User userEntity = userService.getUserByUsername(username);

        if (userEntity == null) {
            // TODO 存在しないログイン情報
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
            userEntity.getUserId().toString(), 
            userEntity.getPasswordHash(), 
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}