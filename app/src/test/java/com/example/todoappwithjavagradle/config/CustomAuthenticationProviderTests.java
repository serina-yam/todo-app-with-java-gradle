package com.example.todoappwithjavagradle.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * CustomAuthenticationProvider のテストクラス
 */
public class CustomAuthenticationProviderTests {

	/**
	 * 認証成功時のテストを行う
	 *
	 * @param userId       ユーザーID
	 * @param username     ユーザー名
	 * @param password     パスワード
	 * @param passwordHash エンコードされたパスワード
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/user_test_data_oauth.csv", numLinesToSkip = 1)
	public void testAuthenticate_Successful(String userId, String username, String password, String passwordHash) {
		// モックの作成
		UserDetailsService userDetailsService = mock(UserDetailsService.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider(userDetailsService,
				passwordEncoder);

		// テストデータの設定
		UserDetails userDetails = new User(username, passwordHash,
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

		// モックの動作設定
		when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		when(passwordEncoder.matches(password, passwordHash)).thenReturn(true);

		// 認証オブジェクトの作成
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);

		// 認証を実行
		assertDoesNotThrow(() -> authenticationProvider.authenticate(authentication));
	}

	/**
	 * 認証失敗時のテストを行う
	 *
	 * @param userId       ユーザーID
	 * @param username     ユーザー名
	 * @param password     パスワード
	 * @param passwordHash エンコードされたパスワード
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/user_test_data_oauth.csv", numLinesToSkip = 1)
	public void testAuthenticate_Failure(String userId, String username, String password, String passwordHash) {
		// モックの作成
		UserDetailsService userDetailsService = mock(UserDetailsService.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
		CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider(userDetailsService,
				passwordEncoder);

		// テストデータの設定
		UserDetails userDetails = new User(username, passwordHash,
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

		// モックの動作設定
		when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
		when(passwordEncoder.matches(password, passwordHash)).thenReturn(false); // 認証失敗

		// 認証オブジェクトの作成
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				password);

		// 認証を実行し、例外がスローされることを検証
		assertThrows(BadCredentialsException.class, () -> authenticationProvider.authenticate(authentication));
	}

	/**
	 * サポートされている認証タイプのテストを行う
	 */
	@Test
	public void supports_SupportedAuthenticationType_ReturnsTrue() {
		// モックの設定
		CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(mock(UserDetailsService.class),
				mock(PasswordEncoder.class));

		// テスト & 検証
		assert (authProvider.supports(UsernamePasswordAuthenticationToken.class));
	}

	/**
	 * サポートされていない認証タイプのテストを行う
	 */
	@Test
	public void supports_UnsupportedAuthenticationType_ReturnsFalse() {
		// モックの設定
		CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(mock(UserDetailsService.class),
				mock(PasswordEncoder.class));

		// テスト & 検証
		assert (!authProvider.supports(Object.class));
	}
}
