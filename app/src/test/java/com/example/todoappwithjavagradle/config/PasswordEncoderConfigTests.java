package com.example.todoappwithjavagradle.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PasswordEncoderConfigTests {

    @Test
    void testPasswordEncoderBean() {
        // テスト用のSpringアプリケーションコンテキストを作成
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // PasswordEncoderConfigクラスをコンテキストに登録
        context.register(PasswordEncoderConfig.class);
        context.refresh(); // コンテキストのリフレッシュ

        // コンテキストからPasswordEncoderのBeanを取得
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        // PasswordEncoderのBeanが正しく定義されていることを確認
        assertNotNull(passwordEncoder);

        // コンテキストをクローズ
        context.close();
    }
}
