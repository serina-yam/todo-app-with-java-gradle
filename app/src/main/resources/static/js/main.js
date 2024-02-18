"use strict";

function validateLoginForm() {
    // ユーザー名の入力値を取得
    var username = document.getElementById("username").value;
    // パスワードの入力値を取得
    var password = document.getElementById("password").value;

    // ユーザー名のバリデーションチェック
    if (username.trim() === "") {
        document.getElementById("usernameError").innerText = "ユーザー名を入力してください";
        document.getElementById("usernameError").style.display = "block";
        return false; // バリデーションエラー
    } else {
        document.getElementById("usernameError").innerText = ""; // エラーメッセージをクリア
        document.getElementById("usernameError").style.display = "none";
    }

    // パスワードのバリデーションチェック
    if (password.trim() === "") {
        document.getElementById("passwordError").innerText = "パスワードを入力してください";
        document.getElementById("passwordError").style.display = "block";
        return false; // バリデーションエラー
    } else {
        document.getElementById("passwordError").innerText = ""; // エラーメッセージをクリア
        document.getElementById("passwordError").style.display = "none";
    }

    return true; // バリデーションOK
}

// フォーム送信時にバリデーション関数を呼び出す
document.getElementById("loginForm").onsubmit = function () {
    return validateLoginForm();
};
