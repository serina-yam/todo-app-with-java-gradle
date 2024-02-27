package com.example.todoappwithjavagradle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;
import com.example.todoappwithjavagradle.util.LoginType;

import jakarta.servlet.http.HttpSession;

/**
 * ユーザーサービスクラス
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpSession httpSession;

    /**
     * プロバイダーからのユーザー登録を処理
     *
     * @param oauth2UserId OAuth2ユーザーID
     * @param username     ユーザー名
     * @param loginType    ログインタイプ
     * @return ユーザーID
     */
    public Integer signupUserFromProvider(String oauth2UserId, String username, String loginType) {
        // GitHub or Google

        // シーケンスからユーザーIDを取得
        Integer userId = userRepository.getNextUserId();

        User user = new User(userId, oauth2UserId, null, username, loginType);

        userRepository.save(user);
        return userId;
    }

    /**
     * フォームからのユーザー登録を処理
     *
     * @param username ユーザー名
     * @param password パスワード
     * @return ユーザーID
     */
    public Integer signupUserFromForm(String username, String password) {
        // パスワードをハッシュ化して保存
        String passwordHash = passwordEncoder.encode(password);

        // シーケンスからユーザーIDを取得
        Integer userId = userRepository.getNextUserId();

        User user = new User(userId, username, passwordHash, null, LoginType.FORM.getValue());

        userRepository.save(user);
        return userId;
    }

    /**
     * ユーザー名からユーザーエンティティを取得
     *
     * @param username ユーザー名
     * @return ユーザーエンティティ
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
