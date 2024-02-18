package com.example.todoappwithjavagradle.config;
import lombok.Getter;

@Getter
public enum LoginType {
    GITHUB("GitHub"),
    GOOGLE("Google"),
    FORM("Form");

    private final String value;

    LoginType(String value) {
        this.value = value;
    }
}
