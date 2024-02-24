package com.example.todoappwithjavagradle.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.util.LoginType;

/**
 * ユーザーリポジトリのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    /**
     * ユーザー名による検索をテスト
     *
     * @param username     ユーザー名
     * @param passwordHash パスワードハッシュ
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testFindByUsername(Integer userId, String loginType, String username, String passwordHash) {
        // モックの設定
        User user = new User(username, passwordHash, null, LoginType.FORM.toString());
        when(userRepository.findByUsername(username)).thenReturn(user);

        // テスト
        User foundUser = userRepository.findByUsername(username);

        // 検証
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        assertEquals(passwordHash, foundUser.getPasswordHash());
    }
}
