package com.example.todoappwithjavagradle.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
@SuppressWarnings("null")
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

    /**
     * ユーザーIDとアイテムリストを指定して、アイテム一覧画面が正しく表示されることをテスト
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testDisplayList_WithValidUserIdAndItemList(Integer userId) {
        // モックの設定
        when(httpSession.getAttribute(AttributeKey.USER_ID.getValue())).thenReturn(userId);

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item());
        when(itemService.searchAll(userId)).thenReturn(itemList);

        // テスト
        String actual = itemController.displayList(model);

        // 検証
        verify(httpSession, times(1)).getAttribute(AttributeKey.USER_ID.getValue());
        verify(itemService, times(1)).searchAll(userId);
        verify(model, times(1)).addAttribute(AttributeKey.ITEM_LIST.getValue(), itemList);
        assertEquals("index", actual);
    }

    /**
     * ユーザーIDが無効な場合、ログインページにリダイレクトされることをテストします。
     */
    @Test
    public void testDisplayList_WithInvalidUserId_RedirectToLoginPage() {
        // モックの設定
        when(httpSession.getAttribute(AttributeKey.USER_ID.getValue())).thenReturn(null);

        // テスト
        String actual = itemController.displayList(model);

        // 検証
        verify(httpSession, times(1)).getAttribute(AttributeKey.USER_ID.getValue());
        verify(itemService, never()).searchAll(any());
        verify(model, never()).addAttribute(any(), any());
        assertEquals("redirect:login", actual);
    }

    /**
     * アイテム一覧の取得中に例外が発生した場合、エラーページが表示されることをテスト
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/user_test_data.csv", numLinesToSkip = 1)
    public void testDisplayList_WithException_HandleError(Integer userId) {
        // モックの設定
        when(httpSession.getAttribute(AttributeKey.USER_ID.getValue())).thenReturn(userId);
        when(itemService.searchAll(1)).thenThrow(RuntimeException.class);

        // テスト
        String actual = itemController.displayList(model);

        // 検証
        verify(httpSession, times(1)).getAttribute(AttributeKey.USER_ID.getValue());
        verify(itemService, times(1)).searchAll(1);
        verify(model, times(1)).addAttribute(eq(AttributeKey.ERROR_MESSAGE.getValue()),
                eq("An error occurred: java.lang.RuntimeException"));
        assertEquals("error", actual);
    }

    /**
     * アイテム追加画面の表示をテスト
     */
    @Test
    public void testDisplayAdd() {
        // テスト
        String actual = itemController.displayAdd(model);

        // 検証
        verify(model, times(1)).addAttribute(AttributeKey.ITEM_REQUEST.getValue(), new ItemRequest());

        assertEquals("item/add", actual);
    }

    /**
     * バインディング結果にエラーがない場合、アイテムを作成し、リダイレクトされることをテスト
     */
    @Test
    public void testCreate_WithoutErrors() {
        ItemRequest itemRequest = new ItemRequest();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = itemController.create(itemRequest, bindingResult, model);

        verify(bindingResult, times(1)).hasErrors();
        verify(model, never()).addAttribute(eq("validationError"), anyList());
        verify(itemService, times(1)).create(itemRequest);
        assertEquals("redirect:/", actual);
    }

    /**
     * バインディング結果にエラーがある場合、エラーメッセージがモデルに追加されることをテスト
     */
    @Test
    public void testCreate_WithErrors_AddsErrorMessagesToModel() {
        // モックの設定
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(getFieldErrors());

        // テスト
        String actual = itemController.create(null, bindingResult, model);

        // 検証
        verify(model, times(2)).addAttribute(any(String.class), any(String.class));
        // 2回のaddAttributeが呼ばれたことを検証し、各フィールドのエラーメッセージがモデルに追加されたことを確認
        assertEquals("item/add", actual);
    }

    /**
     * テスト用<br>
     * フィールドエラーを含むリストを作成
     * 
     * @return テスト用のフィールドエラーのリスト
     */
    private List<FieldError> getFieldErrors() {
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("itemRequest", "fieldName1", "Error message 1"));
        fieldErrors.add(new FieldError("itemRequest", "fieldName2", "Error message 2"));
        return fieldErrors;
    }

    /**
     * アイテム作成中に例外が発生した場合、エラーページが表示されることをテスト
     */
    @Test
    public void testCreate_WithException_HandleError() {
        // モックの設定
        doThrow(new RuntimeException("Error creating item")).when(itemService).create(any(ItemRequest.class));

        // テスト
        String actual = itemController.create(new ItemRequest(), mock(BindingResult.class), model);

        // 検証
        verify(itemService, times(1)).create(any(ItemRequest.class));
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("An error occurred: Error creating item"));
        assertEquals("error", actual);
    }

    /**
     * アイテムの状態を更新するメソッドのテスト
     * 
     * @param id     アイテムID
     * @param userId ユーザーID
     * @param title  タイトル
     * @param state  状態
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testUpdate(Integer id, Integer userId, String title, Integer state) {
        String actual = itemController.update(id, state, model);

        verify(itemService, times(1)).update(id, state);
        assertEquals("redirect:/", actual);
    }

    /**
     * アイテムの状態更新中に例外が発生した場合、エラーページが表示されることをテスト
     *
     * @param id アイテムID
     */
    @Test
    public void testUpdate_WithException_HandleError() {
        // itemServiceのupdateメソッドが例外をスローするようにモックする
        doThrow(new RuntimeException("Error updating item")).when(itemService).update(any(Integer.class),
                any(Integer.class));

        // テスト
        String actual = itemController.update(1, 1, model);

        // 検証
        verify(itemService, times(1)).update(1, 1);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("An error occurred: Error updating item"));
        assertEquals("error", actual);
    }

    /**
     * アイテムの削除をテスト
     * 
     * @param id アイテムID
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testDelete(Integer id) {
        // テスト
        itemController.delete(id, model);

        // 検証
        verify(itemService, times(1)).delete(id);
    }

    /**
     * アイテムの削除中に例外が発生した場合、エラーメッセージをモデルに追加し、エラーページが表示されることをテスト
     *
     * @param id アイテムID
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/item_test_data.csv", numLinesToSkip = 1)
    public void testDelete_WithException_HandleError(Integer id) {
        // itemServiceのdeleteメソッドが例外をスローするようにモックする
        doThrow(new RuntimeException("Error deleting item")).when(itemService).delete(any(Integer.class));

        // テスト
        String actual = itemController.delete(id, model);

        // 検証
        verify(itemService, times(1)).delete(id);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("An error occurred: Error deleting item"));
        assertEquals("error", actual);
    }
}
