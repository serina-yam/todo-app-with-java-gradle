package com.example.todoappwithjavagradle.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * カスタム認証プロバイダークラス
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * コンストラクタ
     *
     * @param userDetailsService ユーザーの詳細情報を提供するサービス
     * @param passwordEncoder    パスワードのエンコーダー
     */
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 認証を実行
     *
     * @param authentication 認証オブジェクト
     * @return 認証に成功した場合は認証結果を表すトークンを返す
     * @throws AuthenticationException 認証に失敗した場合
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(inputPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, inputPassword, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    /**
     * 指定された認証オブジェクトがこのプロバイダーによってサポートされるかどうかを示す
     *
     * @param authentication 認証オブジェクト
     * @return このプロバイダーが指定された認証オブジェクトをサポートする場合は true、それ以外の場合は false
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // ユーザー名とパスワードによる認証をサポート
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
