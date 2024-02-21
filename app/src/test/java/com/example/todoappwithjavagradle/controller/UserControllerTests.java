package com.example.todoappwithjavagradle.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todoappwithjavagradle.service.UserService;

/**
 * ユーザーコントローラーのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    /**
     * 登録画面を表示するメソッドのテスト
     */
    @Test
    public void testShowSignupForm() {
        // テスト
        String viewName = userController.showSignupForm();

        // 検証
        assert(viewName.equals("signup"));
    }

    /**
     * ユーザーを登録するメソッドのテスト
     *
     * @param userId      ユーザーID
     * @param username    ユーザー名
     * @param passwordHash パスワードハッシュ
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testSignupUser(Integer userId, String username, String passwordHash) {

        // テスト
        String viewName = userController.signupUser(username, passwordHash, null);

        // 検証
        assert(viewName.equals("redirect:/login"));
        verify(userService, times(1)).signupUserFromForm(anyString(), anyString());
    }
}
