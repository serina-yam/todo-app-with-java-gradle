package com.example.todoappwithjavagradle.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.todoappwithjavagradle.Form.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.service.ItemService;
import com.example.todoappwithjavagradle.util.AttributeKey;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

/**
 * アイテムコントローラーのテストクラス
 */
@ExtendWith(MockitoExtension.class)
public class ItemControllerTests {

    @Mock
    private ItemService itemService;

    @Mock
    private Model model;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private ItemController itemController;

    private Integer userId = 1;

    /**
     * アイテムリストの表示をテスト
     */
    @Test
    public void testDisplayList() {
        // モックの設定
        List<Item> itemList = new ArrayList<>();
        when(itemService.searchAll(userId)).thenReturn(itemList);
        when(httpSession.getAttribute(AttributeKey.USER_ID.getValue())).thenReturn(userId);


        // テスト
        itemController.displayList(model);

        // 検証
        verify(itemService, times(1)).searchAll(userId);
        verify(model, times(1)).addAttribute("itemlist", itemList);
    }

    /**
     * アイテム追加画面の表示をテスト
     */
    @Test
    public void testDisplayAdd() {
        // テスト
        String result = itemController.displayAdd(model);

        // 検証
        verify(model, times(1)).addAttribute(AttributeKey.ITEM_LIST.getValue(), new ItemRequest());

        assertEquals("/item/add", result);
    }

    /**
     * エラーがある場合のアイテム作成をテスト
     */
    @Test
    public void testCreateWithErrors() {
        // モックの設定
        ItemRequest itemRequest = new ItemRequest();
        BindingResult bindingResult = mock(BindingResult.class);        
        when(bindingResult.hasErrors()).thenReturn(true); // エラーの中身はFormのテストで補完する
    
        // テスト
        String result = itemController.create(itemRequest, bindingResult, model);
    
        // 検証
        verify(bindingResult, times(1)).hasErrors();
        verify(itemService, never()).create(any(ItemRequest.class));

        assertEquals("/item/add", result);

    }
    

    /**
     * エラーがない場合のアイテム作成をテスト
     */
    @SuppressWarnings("null")
    @Test
    public void testCreateWithoutErrors() {
        // モックの設定
        ItemRequest itemRequest = new ItemRequest();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        // テスト
        itemController.create(itemRequest, bindingResult, model);

        // 検証
        verify(bindingResult, times(1)).hasErrors();
        verify(model, never()).addAttribute(eq("validationError"), anyList());
        verify(itemService, times(1)).create(itemRequest);
    }

    /**
     * アイテムの削除をテスト
     */
    @Test
    public void testDelete() {
        // テスト
        itemController.delete(1, model);

        // 検証
        verify(itemService, times(1)).delete(1);
    }
}
