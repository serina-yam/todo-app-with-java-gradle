package com.example.todoappwithjavagradle.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

// TODO テスト修正する
public class CustomAuthenticationProviderTests {

	// @Test
	// public void authenticate_ValidCredentials_SuccessfulAuthentication() {
	// // モックの設定
	// UserDetailsService userDetailsService = mock(UserDetailsService.class);
	// PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	// CustomAuthenticationProvider authProvider = new
	// CustomAuthenticationProvider(userDetailsService,
	// passwordEncoder);

	// String username = "testUser";
	// String password = "testPassword";
	// UserDetails userDetails = User.withUsername(username)
	// .password(passwordEncoder.encode(password))
	// .roles("USER")
	// .build();

	// when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
	// when(passwordEncoder.matches(password,
	// userDetails.getPassword())).thenReturn(true);

	// UsernamePasswordAuthenticationToken authenticationToken = new
	// UsernamePasswordAuthenticationToken(username,
	// password);

	// // テスト
	// UserDetails authenticatedUser = (UserDetails)
	// authProvider.authenticate(authenticationToken).getPrincipal();

	// // 検証
	// assertEquals(userDetails, authenticatedUser);
	// }

	// @Test
	// public void authenticate_InvalidCredentials_BadCredentialsExceptionThrown() {
	// // モックの設定
	// UserDetailsService userDetailsService = mock(UserDetailsService.class);
	// PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	// CustomAuthenticationProvider authProvider = new
	// CustomAuthenticationProvider(userDetailsService,
	// passwordEncoder);

	// String username = "testUser";
	// String password = "testPassword";
	// UserDetails userDetails = User.withUsername(username)
	// .password(passwordEncoder.encode(password))
	// .roles("USER")
	// .build();

	// when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
	// when(passwordEncoder.matches(password,
	// userDetails.getPassword())).thenReturn(false);

	// UsernamePasswordAuthenticationToken authenticationToken = new
	// UsernamePasswordAuthenticationToken(username,
	// password);

	// // テスト & 検証
	// org.junit.jupiter.api.Assertions.assertThrows(BadCredentialsException.class,
	// () -> {
	// authProvider.authenticate(authenticationToken);
	// });
	// }

	@Test
	public void supports_SupportedAuthenticationType_ReturnsTrue() {
		// モックの設定
		CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(mock(UserDetailsService.class),
				mock(PasswordEncoder.class));

		// テスト & 検証
		assert (authProvider.supports(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	public void supports_UnsupportedAuthenticationType_ReturnsFalse() {
		// モックの設定
		CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(mock(UserDetailsService.class),
				mock(PasswordEncoder.class));

		// テスト & 検証
		assert (!authProvider.supports(Object.class));
	}
}
