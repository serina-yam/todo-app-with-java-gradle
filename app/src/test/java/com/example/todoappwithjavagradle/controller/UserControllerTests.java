package com.example.todoappwithjavagradle.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.service.UserService;
import com.example.todoappwithjavagradle.util.LoginType;

/**
 * ユーザーコントローラーのテストクラス
 */
@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private Model model;

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
        assert (viewName.equals("signup"));
    }

    /**
     * ユーザーを登録するメソッドのテスト<br>
     * 登録完了
     *
     * @param userId       ユーザーID
     * @param username     ユーザー名
     * @param passwordHash パスワードハッシュ
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testSignupUser_WhenUserDoesNotExist_ThenRedirectToLogin(Integer userId, String loginType,
            String username, String passwordHash) {

        // テスト
        String viewName = userController.signupUser(username, passwordHash, model);

        // 検証
        assert (viewName.equals("login"));
        verify(userService, times(1)).signupUserFromForm(anyString(), anyString());
    }

    /**
     * ユーザーを登録するメソッドのテスト<br>
     * 既存データあり
     *
     * @param userId       ユーザーID
     * @param username     ユーザー名
     * @param passwordHash パスワードハッシュ
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testSignupUser_WhenUserExists_ThenRedirectToSignupWithErrorMessage(Integer userId, String loginType,
            String username, String passwordHash) {

        Model model = mock(Model.class);
        User user = new User(1, username, passwordHash, null, LoginType.FORM.getValue());
        when(userService.getUserByUsername(username)).thenReturn(user);
        // テスト
        String actual = userController.signupUser(username, passwordHash, model);

        // 検証
        assertEquals(actual, "signup");
        verify(model).addAttribute(eq("errorMessage"), anyString());
    }

    /**
     * ユーザーを登録するメソッドのテスト<br>
     * 例外発生
     */
    @Test
    public void testSignupUser_WhenExceptionThrown_ThenHandleError() {
        // モックの設定
        String username = "testUser";
        String password = "testPassword";
        Model model = mock(Model.class);
        when(userService.getUserByUsername(username)).thenThrow(RuntimeException.class);

        // テスト実行
        String actual = userController.signupUser(username, password, model);

        // 検証
        assertEquals("error", actual); // エラーページにリダイレクトされることを想定しているため、仮の値として "error" を設定
        verify(model).addAttribute(eq("errorMessage"), anyString()); // エラーメッセージがモデルに追加されることを確認
    }
}
