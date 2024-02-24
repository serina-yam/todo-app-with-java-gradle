package com.example.todoappwithjavagradle.controller;

import com.example.todoappwithjavagradle.entity.User;
import com.example.todoappwithjavagradle.repository.UserRepository;
import com.example.todoappwithjavagradle.service.UserService;
import com.example.todoappwithjavagradle.util.LoginType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    /**
     * 有効なユーザー名でのログイン成功をテスト
     */
    @Test
    void testLoginSuccess_WithValidUsername() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(new User());

        // テスト実行
        String actual = loginController.loginSuccess(authentication, redirectAttributes);

        // 結果の検証
        assertEquals("redirect:/", actual);
        verify(httpSession, times(2)).setAttribute(anyString(), any());
    }

    /**
     * OAuth2ユーザーがGitHubでログインした場合のテスト
     */
    @Test
    void testLoginSuccess_OAuth2User_GitHub() {
        // モックの設定
        String oauth2UserId = "githubId";
        String oauth2UserName = "githubUsername";

        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", oauth2UserId);
        attributes.put("login", oauth2UserName);
        when(oAuth2User.getAttributes()).thenReturn(attributes);
        when(oAuth2User.getAttribute(anyString())).thenReturn("dummy");

        // userRepository.findByOauth2UserId()がユーザーオブジェクトを返すように設定
        User user = new User(oauth2UserName, null, oauth2UserId, LoginType.GITHUB.getValue());
        when(userRepository.findByOauth2UserId(anyString())).thenReturn(user);

        // テスト実行
        String actual = loginController.loginSuccess(authentication, redirectAttributes);

        // 検証
        assertEquals("redirect:/", actual);
        verify(httpSession, times(2)).setAttribute(anyString(), any());
    }

    /**
     * OAuth2ユーザーがGoogleでログインした場合のテスト
     */
    @Test
    void testLoginSuccess_OAuth2User_Google() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "googleSub");
        attributes.put("name", "googleName");
        when(oAuth2User.getAttributes()).thenReturn(attributes);
        when(oAuth2User.getAttribute(anyString())).thenReturn("dummy");

        // テスト実行
        String actual = loginController.loginSuccess(authentication, redirectAttributes);

        // 検証
        assertEquals("redirect:/", actual);
        verify(httpSession, times(2)).setAttribute(anyString(), any());
    }

    /**
     * 認証が未認証状態でログイン成功した場合のテスト
     */
    @Test
    void testLoginSuccess_WhenAuthenticationNotAuthenticated() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // テスト実行
        String actual = loginController.loginSuccess(authentication, redirectAttributes);

        // 結果の検証
        assertEquals("redirect:/", actual);
        verify(httpSession, never()).setAttribute(anyString(), any());
    }

    /**
     * ユーザー名が見つからない場合のログイン成功をテスト
     */
    @Test
    void testLoginSuccess_UsernameNotFoundException() {
        // モックの設定
        Authentication authentication = mock(Authentication.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(null);

        // テスト実行
        try {
            loginController.loginSuccess(authentication, redirectAttributes);
            fail("Expected UsernameNotFoundException was not thrown");
        } catch (UsernameNotFoundException e) {
            // 例外がスローされた場合はテストが成功
            assertNotNull(e.getMessage());
        }

        // 検証
        verify(httpSession, never()).setAttribute(anyString(), any());
    }

    /**
     * ログインページを取得するメソッドのテスト<br>
     * 認証状態を保持した状態のテスト方法は不明のため実施しない
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
