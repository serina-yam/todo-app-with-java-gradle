package com.example.todoappwithjavagradle.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;

import com.example.todoappwithjavagradle.config.AttributeKey;
import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.service.ItemService;
import com.example.todoappwithjavagradle.util.ErrorHandlingUtil;

import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    HttpSession httpSession;

    /**
     * アイテム情報一覧画面を表示
     * @param model Model
     * @return アイテム情報一覧画面のHTML
     */
    @SuppressWarnings("null")
    @GetMapping(value = "/")
    public String displayList(Model model) {
        try {
            Integer userId = (Integer) httpSession.getAttribute(AttributeKey.USER_ID.getValue());
            if (userId == null) {
                // userIdがセッションにない場合はログイン画面にリダイレクトする
                return "redirect:/login";
            }
            List<Item> itemlist = itemService.searchAll(userId);
            model.addAttribute(AttributeKey.ITEM_LIST.getValue(), itemlist);
            model.addAttribute(AttributeKey.USERNAME.getValue(), httpSession.getAttribute(AttributeKey.USERNAME.getValue()));
            return "index";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * アイテム新規登録画面を表示
     * @param model Model
     * @return アイテム新規登録画面のHTML
     */
    @SuppressWarnings("null")
    @GetMapping(value = "/item/add")
    public String displayAdd(Model model) {
        model.addAttribute(AttributeKey.ITEM_LIST.getValue(), new ItemRequest());
        return "/item/add";
    }

    /**
     * アイテム新規登録
     * @param itemRequest リクエストデータ
     * @param model Model
     * @return アイテム情報一覧画面
     */
    @PostMapping(value = "/item/create")
    public String create(@Validated @ModelAttribute ItemRequest itemRequest, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            // 入力チェックエラーの場合はエラーメッセージを表示して登録画面に戻る
            for (FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            return "/item/add";
        }

        try {
            itemService.create(itemRequest);
            return "redirect:/";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

     /**
      * アイテム情報 削除
      * @param id
      * @param model
      * @return アイテム情報一覧画面
      */
    @PostMapping(value = "/item/delete")
    public String delete(@RequestParam("id") Integer id, Model model) {
        try {
            itemService.delete(id);
            return "redirect:/";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * 共通のエラーハンドリングメソッド
     * @param ex 発生した例外
     * @param model Model
     * @return エラーページのHTML
     */
    private String handleError(Exception ex, Model model) {
        return ErrorHandlingUtil.handleError(ex, model);
    }
}
