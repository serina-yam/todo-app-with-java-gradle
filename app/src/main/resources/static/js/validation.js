"use strict";

/**
 * ログイン情報入力値 バリデーションチェック
 * @returns true: バリデーションOK, false: バリデーションエラー
 */
function validateLoginForm() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // ユーザー名のバリデーションチェック
    if (username.trim() === "") {
        document.getElementById("usernameError").innerText = "ユーザー名を入力してください";
        document.getElementById("usernameError").style.display = "block";
        return false; // バリデーションエラー
    } else {
        document.getElementById("usernameError").innerText = "";
        document.getElementById("usernameError").style.display = "none";
    }

    // パスワードのバリデーションチェック
    if (password.trim() === "") {
        document.getElementById("passwordError").innerText = "パスワードを入力してください";
        document.getElementById("passwordError").style.display = "block";
        return false; // バリデーションエラー
    } else {
        document.getElementById("passwordError").innerText = "";
        document.getElementById("passwordError").style.display = "none";
    }

    return true; // バリデーションOK
}

// フォーム送信時にバリデーション関数を呼び出す
document.getElementById("loginForm").onsubmit = function () {
    return validateLoginForm();
};
