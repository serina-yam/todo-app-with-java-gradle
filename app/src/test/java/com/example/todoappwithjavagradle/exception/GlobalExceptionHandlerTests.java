package com.example.todoappwithjavagradle.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * GlobalExceptionHandler のテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTests {

	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;

	/**
	 * handleException メソッドがエラーページとエラーメッセージを返すことを検証
	 */
	@Test
	void handleException_ShouldReturnErrorPageWithErrorMessage() {

		String errorMessage = "Test error message";

		// テスト
		ModelAndView modelAndView = globalExceptionHandler.handleException(new Exception(errorMessage));

		// 検証
		assertEquals("error", modelAndView.getViewName());
		assertEquals(errorMessage, modelAndView.getModel().get("errorMessage"));
	}
}
