package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.todoappwithjavagradle.util.LoginType;

/**
 * ユーザーコントローラーのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    /**
     * サインアップフォームを表示するメソッドのテスト
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testLoadUserByUsername(Integer userId, String username, String passwordHash) {
        // モックの設定
        com.example.todoappwithjavagradle.entity.User user = new com.example.todoappwithjavagradle.entity.User(username, passwordHash, null, LoginType.FORM.toString());
        user.setUserId(userId);
        when(userService.getUserByUsername(username)).thenReturn(user);

        // テスト
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // 検証
        assertNotNull(userDetails);
        assertEquals(userId.toString(), userDetails.getUsername());
        assertEquals(passwordHash, userDetails.getPassword());
        assertEquals(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), userDetails.getAuthorities());
    }

    /**
     * ユーザーをサインアップするメソッドのテスト
     *
     * @param userId       ユーザーID
     * @param username     ユーザー名
     * @param passwordHash パスワードハッシュ
     */
    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // モックの設定
        String username = "nonExistentUser";
        when(userService.getUserByUsername(username)).thenReturn(null);

        // テストと検証
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(username)
        );
        assertEquals("User not found: " + username, exception.getMessage());
    }
}
