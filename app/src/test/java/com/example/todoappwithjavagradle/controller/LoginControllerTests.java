package com.example.todoappwithjavagradle.controller;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;
import com.example.todoappwithjavagradle.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * ログインコントローラーのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {

    @Mock
    private Model model;

    @Mock
    UserRepository userRepository;

    @Mock
    UserService userService;

    @Mock
    ClientRegistrationRepository clientRegistrationRepository;

    @Mock
    HttpSession httpSession;

    @InjectMocks
    LoginController loginController;

    @Test
    void testLoginSuccess() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(new User());

        // テスト実行
        String result = loginController.loginSuccess(authentication, redirectAttributes);

        // 結果の検証
        assertEquals("redirect:/", result);
        verify(httpSession, times(2)).setAttribute(anyString(), any());
    }

    @Test
    void testLoginSuccessOAuth2User() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttributes()).thenReturn(new HashMap<>());

        // テスト実行
        String result = loginController.loginSuccess(authentication, redirectAttributes);

        // 結果の検証
        assertEquals("redirect:/", result);
        verify(httpSession, times(2)).setAttribute(anyString(), any());
    }

    /**
     * ログインページを取得するメソッドのテスト
     */
    @Test
    public void testGetLoginPage() {

        // テスト対象メソッドの呼び出し
        String actual = null;
        try {
            actual = loginController.getLoginPage(model);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        // 検証
        assertEquals("login", actual);
    }

}
