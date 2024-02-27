package com.example.todoappwithjavagradle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.exception.ErrorHandling;
import com.example.todoappwithjavagradle.service.UserService;
import com.example.todoappwithjavagradle.util.AttributeKey;

/**
 * ユーザー関連のコントローラークラス
 */
@Controller
@RequestMapping("/signup")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * サインアップフォームを表示する
     * 
     * @return サインアップフォームのテンプレート名
     */
    @GetMapping
    public String showSignupForm() {
        return "signup";
    }

    /**
     * ユーザーをサインアップする
     * 
     * @param username ユーザー名
     * @param password パスワード
     * @param model    モデル
     * @return ログイン画面にリダイレクトする
     */
    @SuppressWarnings("null")
    @PostMapping
    public String signupUser(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model) {
        try {

            User existUser = userService.getUserByUsername(username);
            if (existUser != null) {
                String errorMessage = "このユーザー名は使用されています";
                model.addAttribute(AttributeKey.ERROR_MESSAGE.getValue(), errorMessage);
                return "signup";
            }

            userService.signupUserFromForm(username, password);

            String successMessage = "登録が完了しました！ログインできます。";
            model.addAttribute(AttributeKey.SUCCESS_MESSAGE.getValue(), successMessage);
            return "login"; // 登録後はログイン画面に遷移
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * エラーハンドリングを行い、エラーページを表示する
     * 
     * @param ex    例外
     * @param model モデル
     * @return エラーページのテンプレート名
     */
    private String handleError(Exception ex, Model model) {
        return ErrorHandling.handleError(ex, model);
    }
}
