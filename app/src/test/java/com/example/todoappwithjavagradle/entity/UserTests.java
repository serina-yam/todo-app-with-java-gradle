package com.example.todoappwithjavagradle.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * ユーザーエンティティのテストクラス
 */
public class UserTests {

	/**
	 * コンストラクタとゲッターのテスト
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
	public void testConstructorAndGetters(Integer userId, String loginType, String username, String passwordHash,
			String oauth2UserId) {

		User user = new User(userId, username, passwordHash, oauth2UserId, loginType);

		assertEquals(userId, user.getUserId());
		assertEquals(username, user.getUsername());
		assertEquals(passwordHash, user.getPasswordHash());
		assertEquals(oauth2UserId, user.getOauth2UserId());
		assertEquals(loginType, user.getLoginType());
	}

	/**
	 * ユーザーの作成時にタイムスタンプが正しく設定されることをテスト
	 */
	@Test
	public void testTimestampsOnCreate() {
		User user = new User();
		assertNull(user.getCreatedAt());
		assertNull(user.getUpdatedAt());

		user.onCreate();

		assertNotNull(user.getCreatedAt());
		assertNotNull(user.getUpdatedAt());
		assertEquals(user.getCreatedAt(), user.getUpdatedAt());
	}

	/**
	 * ユーザーの更新時にタイムスタンプが正しく更新されることをテスト
	 */
	@Test
	public void testTimestampsOnUpdate() {
		User user = new User();
		user.onCreate();
		Timestamp createdAt = user.getCreatedAt();

		try {
			// 早すぎてcreatedAtとupdatedAtが同じになるためスリープ処理を入れる
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}

		// Update
		user.onUpdate();

		Timestamp updatedAt = user.getUpdatedAt();
		assertNotNull(updatedAt);
		assertEquals(createdAt, user.getCreatedAt()); // createdAtは、更新されていないこと
		assertNotEquals(createdAt, updatedAt);
	}
}
