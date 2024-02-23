package com.example.todoappwithjavagradle.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class ItemTests {

	@Test
	public void testConstructorAndGetters() {
		Integer id = 1;
		Integer userId = 123;
		String title = "Test Title";
		Integer state = 0;
		Date timeLimit = new Date();

		Item item = new Item(id, userId, title, state, timeLimit, null, null);

		assertEquals(id, item.getId());
		assertEquals(userId, item.getUserId());
		assertEquals(title, item.getTitle());
		assertEquals(state, item.getState());
		assertEquals(timeLimit, item.getTimeLimit());
	}

	@Test
	public void testTimestampsOnCreate() {
		Item item = new Item();
		assertNull(item.getCreatedAt());
		assertNull(item.getUpdatedAt());

		item.onCreate();

		assertNotNull(item.getCreatedAt());
		assertNotNull(item.getUpdatedAt());
		assertEquals(item.getCreatedAt(), item.getUpdatedAt());
	}

	@Test
	public void testTimestampsOnUpdate() {
		Item item = new Item();
		item.onCreate();
		Timestamp createdAt = item.getCreatedAt();

		try {
			// 早すぎてcreatedAtとupdatedAtが同じになるためスリープ処理を入れる
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		}

		// Update
		item.onUpdate();

		Timestamp updatedAt = item.getUpdatedAt();
		assertNotNull(updatedAt);
		assertEquals(createdAt, item.getCreatedAt()); // CreatedAt should not change on update
		assertNotEquals(createdAt, updatedAt);
	}
}
