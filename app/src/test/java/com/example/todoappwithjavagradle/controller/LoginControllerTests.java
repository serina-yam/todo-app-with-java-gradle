package com.example.todoappwithjavagradle.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ログインコントローラーのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerTests {

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    /**
     * ログインページを取得するメソッドのテスト
     */
    @Test
    public void testGetLoginPage() {
    
        // テスト対象メソッドの呼び出し
        String result = null;
        try {
            result = loginController.getLoginPage(model);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    
        // 検証
        assertEquals("login", result);
    }
    
}
