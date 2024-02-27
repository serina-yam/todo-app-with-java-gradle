package com.example.todoappwithjavagradle.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * PasswordEncoderConfigのテストクラス
 */
public class PasswordEncoderConfigTests {

    /**
     * PasswordEncoderのBeanが正しく定義されていることをテスト
     */
    @Test
    void testPasswordEncoderBean() {
        // テスト用のSpringアプリケーションコンテキストを作成
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // PasswordEncoderConfigクラスをコンテキストに登録
        context.register(PasswordEncoderConfig.class);
        context.refresh(); // コンテキストのリフレッシュ

        // テスト
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        // 検証
        assertNotNull(passwordEncoder);

        // コンテキストをクローズ
        context.close();
    }
}
