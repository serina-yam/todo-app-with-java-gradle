package com.example.todoappwithjavagradle.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * アイテムエンティティのテストクラス
 */
public class ItemTests {

	/**
	 * コンストラクタとゲッターのテスト
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
	public void testConstructorAndGetters(Integer id, Integer userId, String title, Integer state) {

		Date timeLimit = new Date();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Item item = new Item(id, userId, title, state, timeLimit, timestamp, timestamp);

		assertEquals(id, item.getId());
		assertEquals(userId, item.getUserId());
		assertEquals(title, item.getTitle());
		assertEquals(state, item.getState());
		assertEquals(timeLimit, item.getTimeLimit());
		assertNotNull(item.getCreatedAt());
		assertNotNull(item.getUpdatedAt());
	}

	/**
	 * アイテムの作成時にタイムスタンプが正しく設定されることをテスト
	 */
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

	/**
	 * アイテムの更新時にタイムスタンプが正しく更新されることをテスト
	 */
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
		assertEquals(createdAt, item.getCreatedAt()); // createdAtは、更新されていないこと
		assertNotEquals(createdAt, updatedAt);
	}
}
