package com.example.todoappwithjavagradle.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.service.ItemService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ItemControllerTests {

    @Mock
    private ItemService itemService;

    @Mock
    private Model model;

    @InjectMocks
    private ItemController itemController;

    @Test
    public void testDisplayList() {
        // モックの設定
        List<Item> itemList = new ArrayList<>();
        when(itemService.searchAll()).thenReturn(itemList);

        // テスト
        itemController.displayList(model);

        // 検証
        verify(itemService, times(1)).searchAll();
        verify(model, times(1)).addAttribute("itemlist", itemList);
    }

    @Test
    public void testDisplayAdd() {
        // テスト
        itemController.displayAdd(model);

        // 検証
        verify(model, times(1)).addAttribute("itemRequest", new ItemRequest());
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateWithErrors() {
        // モックの設定
        ItemRequest itemRequest = new ItemRequest();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // テスト
        itemController.create(itemRequest, bindingResult, model);

        // 検証
        verify(bindingResult, times(1)).hasErrors();
        verify(model, times(1)).addAttribute(eq("validationError"), anyList());
        verify(itemService, never()).create(any(ItemRequest.class));
    }


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

    @Test
    public void testDelete() {
        // テスト
        itemController.delete(1);

        // 検証
        verify(itemService, times(1)).delete(1);
    }
}
