package com.example.todoappwithjavagradle.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    // @ExceptionHandler(ItemNotFoundException.class)
    // public ModelAndView handleItemNotFoundException(ItemNotFoundException ex) {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("error");
    //     modelAndView.addObject("errorMessage", ex.getMessage());
    //     return modelAndView;
    // }

    // @ExceptionHandler(UsernameNotFoundException.class)
    // public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException ex) {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("login");
    //     modelAndView.addObject("errorMessage", "ログイン情報が正しくありません。再度試してください。");
    //     return modelAndView;
    // }
}