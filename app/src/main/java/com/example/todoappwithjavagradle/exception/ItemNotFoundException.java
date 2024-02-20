package com.example.todoappwithjavagradle.exception;

/**
 * アイテムが見つからない場合にスローされる例外
 */
public class ItemNotFoundException extends RuntimeException {

    /**
     * コンストラクタ
     * 
     * @param message 例外メッセージ
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
