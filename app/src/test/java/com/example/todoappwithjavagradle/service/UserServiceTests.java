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

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;
import com.example.todoappwithjavagradle.util.LoginType;

/**
 * ユーザーサービスのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    /**
     * フォームからユーザーをサインアップするメソッドのテスト
     *
     * @throws Exception 例外
     */
    @SuppressWarnings("null")
    @Test
    void testSignupUserFromForm() {
        // テストデータの設定
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedTestPassword";
        User savedUser = new User(username, encodedPassword, null, LoginType.FORM.getValue());
        savedUser.setUserId(1); // 保存後のユーザーID

        // passwordEncoder.encode()のスタブ化
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // userRepository.save()のスタブ化
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // テスト
        Integer userId = userService.signupUserFromForm(username, password);

        // 検証
        verify(passwordEncoder, times(1)).encode(password); // passwordEncoder.encode()が1回呼び出されたことを検証
        verify(userRepository, times(1)).save(any(User.class)); // userRepository.save()が1回呼び出されたことを検証
        assertEquals(savedUser.getUserId(), userId); // 期待されるユーザーIDが返されたことを検証
    }

    /**
     * ユーザー名からユーザーを取得するメソッドのテスト
     *
     * @param username ユーザー名
     * @param encodedPassword エンコードされたパスワード
     * @throws Exception 例外
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testGetUserByUsername(String username, String encodedPassword) {
        // モックの設定
        User user = new User(username, encodedPassword, null, LoginType.FORM.toString());
        when(userRepository.findByUsername(username)).thenReturn(user);

        // テスト
        User retrievedUser = userService.getUserByUsername(username);

        // 検証
        assertEquals(user, retrievedUser);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
