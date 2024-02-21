package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todoappwithjavagradle.Form.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;
import com.example.todoappwithjavagradle.util.AttributeKey;

import jakarta.servlet.http.HttpSession;

/**
 * アイテムサービスのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class ItemServiceTests {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private ItemService itemService;

    /**
     * 指定されたユーザーIDに関連するアイテム情報を検索する
     *
     * @param id        アイテムID
     * @param userId    ユーザーID
     * @param title     タイトル
     * @param state     状態
     * @param timeLimit 期限
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testSearchAll(Integer id, Integer userId, String title, Integer state, Date timeLimit) {
        // テストデータ
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item());

        // モックの設定
        when(itemRepository.findByUserIdOrderByCreatedAtAsc(userId)).thenReturn(itemList);

        // テスト
        List<Item> result = itemService.searchAll(userId);

        // 検証
        verify(itemRepository, times(1)).findByUserIdOrderByCreatedAtAsc(userId);
        assertSame(itemList, result);
    }

    /**
     * アイテムの作成をテスト
     *
     * @param id        アイテムID
     * @param userId    ユーザーID
     * @param title     タイトル
     * @param state     状態
     * @param timeLimit 期限
     * @throws ParseException パース例外
     */
    @SuppressWarnings("null")
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testCreate(Integer id, Integer userId, String title, Integer state, Date timeLimit)
            throws ParseException {
        // モックの設定
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle(title);
        itemRequest.setTimeLimit(timeLimit);

        when(httpSession.getAttribute(AttributeKey.USER_ID.getValue())).thenReturn(userId);

        // テスト
        itemService.create(itemRequest);

        // 検証
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    /**
     * アイテムの状態更新をテスト
     *
     * @param id    アイテムID
     * @param state 更新する状態
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testUpdate(Integer id, Integer userId, String title, Integer state) {
        // テスト
        itemService.update(id, state);

        // 検証
        verify(itemRepository, times(1)).updateStateById(id, state);
    }

    /**
     * アイテムの削除をテスト
     *
     * @param id アイテムID
     */
    @SuppressWarnings("null")
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testDelete(Integer id) {
        // テスト
        itemService.delete(id);

        // 検証
        verify(itemRepository, times(1)).deleteById(id);
    }
}
