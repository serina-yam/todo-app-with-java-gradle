package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @SuppressWarnings("null")
    @Test
    public void testRegisterUser() {
        // モックの設定
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedTestPassword";
        User savedUser = new User(username, encodedPassword);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // テスト
        userService.registerUser(username, password);

        // 検証
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testGetUserByUsername(String username, String encodedPassword) {
        // モックの設定
        User user = new User(username, encodedPassword);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // テスト
        User retrievedUser = userService.getUserByUsername(username);

        // 検証
        assertEquals(user, retrievedUser);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
