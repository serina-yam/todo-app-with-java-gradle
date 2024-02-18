package com.example.todoappwithjavagradle.util;

import org.springframework.ui.Model;

import com.example.todoappwithjavagradle.config.AttributeKey;

/**
 * 共通のエラー処理.
 */
public class ErrorHandlingUtil {

    @SuppressWarnings("null")
    public static String handleError(Exception ex, Model model) {
        model.addAttribute(AttributeKey.ERROR_MESSAGE.getValue(), "An error occurred: " + ex.getMessage());
        return "error";
    }
}
