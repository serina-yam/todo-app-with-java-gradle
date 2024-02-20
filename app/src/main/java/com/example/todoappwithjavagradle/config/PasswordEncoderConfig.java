package com.example.todoappwithjavagradle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードエンコーダーの設定クラス
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * BCryptPasswordEncoderをBeanとして登録
     *
     * @return BCryptPasswordEncoderオブジェクト
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
