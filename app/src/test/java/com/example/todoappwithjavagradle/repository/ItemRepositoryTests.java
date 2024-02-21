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
	@Test
	public void testFindByUserIdOrderByCreatedAtAsc() {
		// テストデータ
		Integer userId = 1;
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item());
		itemList.add(new Item());

		// モックの設定
		when(itemRepository.findByUserIdOrderByCreatedAtAsc(userId)).thenReturn(itemList);

		// テスト
		List<Item> result = itemRepository.findByUserIdOrderByCreatedAtAsc(userId);

		// 検証
		verify(itemRepository, times(1)).findByUserIdOrderByCreatedAtAsc(userId);
		assertEquals(itemList, result);
	}

	/**
	 * 指定されたIDのアイテムの状態を更新するテストメソッド
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
}
