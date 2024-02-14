package com.example.todoappwithjavagradle.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.example.todoappwithjavagradle.entity.User;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testFindByUsername(String username, String passwordHash) {
        // モックの設定
        User user = new User(username, passwordHash);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // テスト
        User foundUser = userRepository.findByUsername(username);

        // 検証
        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        assertEquals(passwordHash, foundUser.getPasswordHash());
    }
}
