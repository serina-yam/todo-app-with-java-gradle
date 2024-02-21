package com.example.todoappwithjavagradle.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * カスタムユーザーの詳細サービスクラス
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    /**
     * コンストラクター
     * 
     * @param userService ユーザーサービス
     */
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザー名によるユーザーの詳細をロード
     * 
     * @param username ユーザー名
     * @return ユーザーの詳細
     * @throws UsernameNotFoundException ユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.todoappwithjavagradle.entity.User userEntity = userService.getUserByUsername(username);

        if (userEntity == null) {
            // 存在しないログイン情報
            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                userEntity.getUserId().toString(),
                userEntity.getPasswordHash(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
