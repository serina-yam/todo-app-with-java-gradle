package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CustomUserDetailsServiceTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testLoadUserByUsername(String username, String passwordHash) {
        // モックの設定
        com.example.todoappwithjavagradle.entity.User user = new com.example.todoappwithjavagradle.entity.User(username, passwordHash);
        when(userService.getUserByUsername(username)).thenReturn(user);

        // テスト
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // 検証
        assertEquals(username, userDetails.getUsername());
        assertEquals(passwordHash, userDetails.getPassword());
        assertEquals(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), userDetails.getAuthorities());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // モックの設定
        String username = "nonExistentUser";
        when(userService.getUserByUsername(username)).thenReturn(null);

        // テストと検証
        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(username)
        );
        assertEquals("User not found: " + username, exception.getMessage());
    }
}
