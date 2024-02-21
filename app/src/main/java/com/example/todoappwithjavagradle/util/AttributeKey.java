package com.example.todoappwithjavagradle.util;

import lombok.Getter;

/**
 * モデルに使用される属性キーを表す列挙型
 */
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