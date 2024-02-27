package com.example.todoappwithjavagradle.exception;

import org.springframework.ui.Model;

import com.example.todoappwithjavagradle.util.AttributeKey;

/**
 * エラー処理を行うユーティリティクラス
 */
public class ErrorHandling {

    /**
     * 例外を処理し、エラーメッセージをモデルに追加してエラーページの表示を行う
     *
     * @param ex    処理する例外
     * @param model エラーメッセージを追加するモデル
     * @return エラーページのビュー名
     */
    @SuppressWarnings("null")
    public static String handleError(Exception ex, Model model) {
        String message = ex.getMessage() == null ? ex.toString() : ex.getMessage();
        model.addAttribute(AttributeKey.ERROR_MESSAGE.getValue(),
                "An error occurred: " + message);
        return "error";
    }
}
