package com.example.todoappwithjavagradle.Form;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * アイテムリクエストのテストクラス
 */
public class ItemRequestTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * タイトルが空でないことをテスト
     */

    @Test
    public void testTitleNotEmpty() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTimeLimit(new Date());

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(itemRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("タイトルを入力してください", violations.iterator().next().getMessage());
    }

    /**
     * タイトルの最大サイズをテスト
     */
    @Test
    public void testTitleMaxSize() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle("msTy3dMLORloOiFsBpKkzuH6Zky0gdaVVfF4UuQPJsk51YqcL7chuDhSCh5FYmLVNvrX5B97l95Pnqndp3IoPtmmBnJ989UzEhKGZ");
        itemRequest.setTimeLimit(new Date());

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(itemRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("タイトルは100桁以内で入力してください", violations.iterator().next().getMessage());
    }

    /**
     * 期限がnullでないことをテスト
     */
    @Test
    public void testTimeLimitNotNull() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setTitle("Test Title");

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(itemRequest);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("期限を入力してください", violations.iterator().next().getMessage());
    }
}
