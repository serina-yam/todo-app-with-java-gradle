package com.example.todoappwithjavagradle.exception;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class ErrorHandlingTests {

	@Mock
	Model model;

	@SuppressWarnings("static-access")
	@Test
	void handleErrorShouldAddErrorMessageToModelAndReturnErrorView() {
		openMocks(this); // モックの初期化

		// 例外のメッセージ
		String errorMessage = "Test error message";

		// テスト用の例外を生成
		Exception testException = new Exception(errorMessage);

		// エラーハンドリングオブジェクトを作成
		ErrorHandling errorHandling = new ErrorHandling();

		// handleErrorメソッドを呼び出し
		String viewName = errorHandling.handleError(testException, model);

		// モデルにエラーメッセージが追加されたことを検証
		verify(model).addAttribute("errorMessage", "An error occurred: " + errorMessage);

		// 正しいビュー名が返されたことを検証
		assertEquals("error", viewName);
	}
}
