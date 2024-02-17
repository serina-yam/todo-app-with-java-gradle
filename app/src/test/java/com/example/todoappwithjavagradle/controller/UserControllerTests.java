package com.example.todoappwithjavagradle.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.todoappwithjavagradle.service.UserService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testShowRegistrationForm() {
        // テスト
        String viewName = userController.showRegistrationForm();

        // 検証
        assert(viewName.equals("registration"));
    }

    @Test
    public void testRegisterUser() {
        // モックの設定
        String username = "testuser";
        String password = "testpassword";

        // テスト
        String viewName = userController.registerUser(username, password);

        // 検証
        assert(viewName.equals("redirect:/login"));
        verify(userService, times(1)).registerUser(anyString(), anyString());
    }
}
