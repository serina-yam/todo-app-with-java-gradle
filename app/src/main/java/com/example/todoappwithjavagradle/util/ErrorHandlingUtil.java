package com.example.todoappwithjavagradle.util;

import org.springframework.ui.Model;

/**
 * エラー処理を行うユーティリティクラス
 */
public class ErrorHandlingUtil {

    /**
     * 例外を処理し、エラーメッセージをモデルに追加してエラーページの表示を行う
     *
     * @param ex    処理する例外
     * @param model エラーメッセージを追加するモデル
     * @return エラーページのビュー名
     */
    @SuppressWarnings("null")
    public static String handleError(Exception ex, Model model) {
        model.addAttribute(AttributeKey.ERROR_MESSAGE.getValue(), "An error occurred: " + ex.getMessage());
        return "error";
    }
}
