package com.example.todoappwithjavagradle.util;
import lombok.Getter;

/**
 * ログインタイプを表す列挙型
 */
@Getter
public enum LoginType {
    GITHUB("GitHub"),
    GOOGLE("Google"),
    FORM("Form");

    private final String value;

    /**
     * ログインタイプのコンストラクタ
     *
     * @param value ログインタイプの値
     */
    LoginType(String value) {
        this.value = value;
    }
}
