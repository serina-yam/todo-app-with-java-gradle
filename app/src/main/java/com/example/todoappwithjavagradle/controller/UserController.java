package com.example.todoappwithjavagradle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todoappwithjavagradle.service.UserService;

@Controller
@RequestMapping("/signup")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm() {
        return "signup"; // signup.htmlを返す
    }

    @PostMapping
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        userService.registerUser(username, password);
        return "redirect:/login"; // 登録後はログインページにリダイレクトする
    }
}
