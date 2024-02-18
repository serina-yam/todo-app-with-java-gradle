package com.example.todoappwithjavagradle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todoappwithjavagradle.config.LoginType;
import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession httpSession;


    public Integer signupUserFromProvider(String oauth2UserId, String username, String loginType) {
        // GitHub or Google
        User user = new User(oauth2UserId, null, username, loginType);
        
        User result = userRepository.save(user);
        Integer userId = result.getUserId();
        return userId;
    }

    public Integer signupUserFromForm(String username, String password) {
        // パスワードをハッシュ化して保存
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword, null, LoginType.FORM.getValue());
        
        User result = userRepository.save(user);
        Integer userId = result.getUserId();
        return userId;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
}
