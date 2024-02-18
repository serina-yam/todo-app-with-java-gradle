package com.example.todoappwithjavagradle.config;

import lombok.Getter;

@Getter
public enum AttributeKey {
    USER_ID("userId"),
    USERNAME("username"),
    ITEM_LIST("itemlist"),
    ITEM_REQUEST("itemRequest"),
    URLS("urls"),
    ERROR_MESSAGE("errorMessage")
    ;

    private final String value;

    AttributeKey(String value) {
        this.value = value;
    }
}
