package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    /**
     * プロバイダーからユーザーをサインアップするメソッドのテスト
     */
    @Test
    public void testSignupUserFromProvider() {
        // テストデータの設定
        String oauth2UserId = "oauth2test";
        String username = "testUser";
        String loginType = LoginType.GITHUB.getValue();
        User savedUser = new User(oauth2UserId, null, username, loginType);
        savedUser.setUserId(1); // 保存後のユーザーID

        // userRepository.save()のスタブ化
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // テスト
        Integer userId = userService.signupUserFromProvider(oauth2UserId, username, loginType);

        // 検証
        verify(userRepository, times(1)).save(any(User.class)); // userRepository.save()が1回呼び出されたことを検証
        assertEquals(savedUser.getUserId(), userId); // 期待されるユーザーIDが返されたことを検証
    }

    /**
     * フォームからユーザーをサインアップするメソッドのテスト
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    void testSignupUserFromForm(Integer userId, String loginType, String username, String passwordHash,
            String oauth2UserId, String password) {
        // テストデータの設定
        User savedUser = new User(username, passwordHash, null, LoginType.FORM.getValue());
        savedUser.setUserId(1); // 保存後のユーザーID

        // passwordEncoder.encode()のスタブ化
        when(passwordEncoder.encode(anyString())).thenReturn(passwordHash);

        // userRepository.save()のスタブ化
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // テスト
        Integer actual = userService.signupUserFromForm(username, password);

        // 検証
        verify(passwordEncoder, times(1)).encode(password); // passwordEncoder.encode()が1回呼び出されたことを検証
        verify(userRepository, times(1)).save(any(User.class)); // userRepository.save()が1回呼び出されたことを検証
        assertEquals(savedUser.getUserId(), actual); // 期待されるユーザーIDが返されたことを検証
    }

    /**
     * ユーザー名からユーザーを取得するメソッドのテスト
     *
     * @param username     ユーザー名
     * @param passwordHash エンコードされたパスワード
     * @throws Exception 例外
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testGetUserByUsername(Integer userId, String loginType, String username, String passwordHash) {
        // モックの設定
        User user = new User(username, passwordHash, null, LoginType.FORM.toString());
        when(userRepository.findByUsername(username)).thenReturn(user);

        // テスト
        User retrievedUser = userService.getUserByUsername(username);

        // 検証
        assertEquals(user, retrievedUser);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
