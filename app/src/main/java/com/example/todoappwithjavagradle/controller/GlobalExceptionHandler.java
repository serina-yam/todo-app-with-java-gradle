package com.example.todoappwithjavagradle.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * アプリケーション全体の例外処理を行う
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 例外が発生した際の処理を行う
     * 
     * @param ex 発生した例外
     * @return エラーページのModelAndViewオブジェクト
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
