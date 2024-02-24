package com.example.todoappwithjavagradle.config;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * SecurityConfig のテストクラス
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTests {

	@Autowired
	private MockMvc mockMvc;

	/**
	 * ログインページへのアクセスをテスト
	 */
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testLoginPageAccess() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/login"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.view().name("login"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * ログアウトURLのテスト
	 */
	@Test
	@WithMockUser(username = "user", roles = "USER")
	public void testLogoutUrl() {
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
					.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
					.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
