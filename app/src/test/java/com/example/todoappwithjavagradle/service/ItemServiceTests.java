package com.example.todoappwithjavagradle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;
import com.example.todoappwithjavagradle.util.DateUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ItemServiceTests {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void testSearchAll() {
        // モックデータの作成
        List<Item> itemList = new ArrayList<>();
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        try {
            itemList.add(new Item(1, 1, "Item 1", 0, DateUtils.parseDate("2024-12-11"), createdAt, updatedAt));
            itemList.add(new Item(2, 1, "Item 2", 0, DateUtils.parseDate("2024-12-11"), createdAt, updatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }


        // モックの動作を
        when(itemRepository.findAll()).thenReturn(itemList);

        // テスト
        List<Item> result = itemService.searchAll();

        // 検証
        assertEquals(itemList.size(), result.size());
        assertEquals(itemList.get(0), result.get(0));
        assertEquals(itemList.get(1), result.get(1));
    }

    @SuppressWarnings("null")
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testCreate(String id, String userId, String title,String state,String timeLimit) throws Exception {

        // モックの動作を設定
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle(title);
        itemRequest.setTimeLimit(new SimpleDateFormat("yyyy-MM-dd").parse(timeLimit));
    
        // テスト
        itemService.create(itemRequest);
    
        // 検証
        verify(itemRepository, times(1)).save(any(Item.class));
    }
    

    @Test
    public void testDelete() {
        // テスト
        itemService.delete(1);

        // 検証
        verify(itemRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NonExistentItem() {
        // 存在しないアイテムのIDを指定して削除を試みる
        itemService.delete(999);

        // 検証: itemRepositoryのdeleteById()メソッドが呼び出されないことを確認する
        verify(itemRepository, never()).deleteById((int) anyLong());
    }
}