package com.example.todoappwithjavagradle.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todoappwithjavagradle.entity.Item;

/**
 * ItemRepository のテストクラス
 */
@SpringBootTest
public class ItemRepositoryTests {

	@Mock
	private ItemRepository itemRepository;

	/**
	 * 指定されたユーザーIDに関連するアイテム情報を作成日時の昇順で取得する
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
	public void testFindByUserIdOrderByCreatedAtAsc(Integer userId) {
		// テストデータ
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item());
		itemList.add(new Item());

		// モックの設定
		when(itemRepository.findByUserIdOrderByIdAsc(userId)).thenReturn(itemList);

		// テスト
		List<Item> actual = itemRepository.findByUserIdOrderByIdAsc(userId);

		// 検証
		verify(itemRepository, times(1)).findByUserIdOrderByIdAsc(userId);
		assertEquals(itemList, actual);
	}

	/**
	 * 指定されたIDのアイテムの状態を更新するテスト
	 *
	 * @param id    アイテムID
	 * @param state 状態
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
	public void testUpdateStateById(Integer id, Integer state) {
		// テスト
		itemRepository.updateStateById(id, state);

		// 検証
		verify(itemRepository, times(1)).updateStateById(id, state);
	}

	/**
	 * シーケンス取得のテスト
	 */
	@Test
	void testGetNextId() {
		// モックの設定
		Integer expectedId = 123; // 仮の次のID
		when(itemRepository.getNextId()).thenReturn(expectedId);

		// テスト実行
		Integer actualId = itemRepository.getNextId();

		// 結果の検証
		assertEquals(expectedId, actualId);
	}
}
