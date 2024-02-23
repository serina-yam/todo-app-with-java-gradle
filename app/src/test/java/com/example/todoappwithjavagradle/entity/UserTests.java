package com.example.todoappwithjavagradle.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

public class UserTests {

	@Test
	public void testConstructorAndGetters() {
		String username = "testUser";
		String passwordHash = "passwordHash";
		String oauth2UserId = "oauth2UserId";
		String loginType = "loginType";

		User user = new User(username, passwordHash, oauth2UserId, loginType);

		assertEquals(username, user.getUsername());
		assertEquals(passwordHash, user.getPasswordHash());
		assertEquals(oauth2UserId, user.getOauth2UserId());
		assertEquals(loginType, user.getLoginType());
	}

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
		assertEquals(createdAt, user.getCreatedAt()); // CreatedAt should not change on update
		assertNotEquals(createdAt, updatedAt);
	}
}
