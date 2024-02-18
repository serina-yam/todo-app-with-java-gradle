package com.example.todoappwithjavagradle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todoappwithjavagradle.service.UserService;
import com.example.todoappwithjavagradle.util.ErrorHandlingUtil;

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
    public String signupUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        try {
            userService.signupUserFromForm(username, password);

            // TODO 登録が成功したことがわかるようにする
            return "redirect:/login"; // 登録後はログイン画面にリダイレクトする
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    private String handleError(Exception ex, Model model) {
        return ErrorHandlingUtil.handleError(ex, model);
    }
}
