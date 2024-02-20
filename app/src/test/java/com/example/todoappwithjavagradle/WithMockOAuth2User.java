package com.example.todoappwithjavagradle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * OAuth2 認証を使用したテスト用のアノテーション
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithOAuth2SecurityContextFactory.class)
public @interface WithMockOAuth2User {

        /**
     * テストユーザーのユーザー名を指定
     * デフォルトは "testUser" 
     * @return ユーザー名
     */
    String username() default "testUser";

}
