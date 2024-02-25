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

import com.example.todoappwithjavagradle.Form.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.exception.ErrorHandling;
import com.example.todoappwithjavagradle.service.ItemService;
import com.example.todoappwithjavagradle.util.AttributeKey;

import jakarta.servlet.http.HttpSession;

/**
 * アイテム関連のコントローラークラス
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    HttpSession httpSession;

    /**
     * アイテム情報一覧画面を表示する
     * 
     * @param model Modelオブジェクト
     * @return アイテム情報一覧画面のHTML
     */
    @SuppressWarnings("null")
    @GetMapping(value = "/")
    public String displayList(Model model) {
        try {
            Object userIdObj = httpSession.getAttribute(AttributeKey.USER_ID.getValue());
            if (userIdObj == null) {
                // userIdがセッションにない場合はログイン画面にリダイレクトする
                return "redirect:login";
            }
            Integer userId = Integer.parseInt(userIdObj.toString());

            List<Item> itemlist = itemService.searchAll(userId);
            model.addAttribute(AttributeKey.ITEM_LIST.getValue(), itemlist);
            model.addAttribute(AttributeKey.USERNAME.getValue(),
                    httpSession.getAttribute(AttributeKey.USERNAME.getValue()));
            return "index";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * アイテム新規登録画面を表示する
     * 
     * @param model Modelオブジェクト
     * @return アイテム新規登録画面のHTML
     */
    @SuppressWarnings("null")
    @GetMapping(value = "/item/add")
    public String displayAdd(Model model) {
        model.addAttribute(AttributeKey.ITEM_REQUEST.getValue(), new ItemRequest());
        return "item/add";
    }

    /**
     * アイテムを新規登録する
     * 
     * @param itemRequest アイテムリクエストデータ
     * @param model       Modelオブジェクト
     * @return アイテム情報一覧画面へのリダイレクト
     */
    @PostMapping(value = "/item/create")
    public String create(@Validated @ModelAttribute ItemRequest itemRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            // 入力チェックエラーの場合はエラーメッセージを表示して登録画面に戻る
            for (FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            return "item/add";
        }

        try {
            itemService.create(itemRequest);
            return "redirect:/";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * アイテムの状態を更新する
     * 
     * @param id    更新対象のアイテムID
     * @param state 更新する状態
     * @param model Modelオブジェクト
     * @return アイテム情報一覧画面へのリダイレクト
     */
    @PostMapping(value = "/item/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("state") Integer state, Model model) {
        try {

            itemService.update(id, state);
            return "redirect:/";
        } catch (Exception ex) {
            return handleError(ex, model);
        }
    }

    /**
     * アイテム情報を削除する
     * 
     * @param id    削除対象のアイテムID
     * @param model Modelオブジェクト
     * @return アイテム情報一覧画面へのリダイレクト
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
     * 共通のエラーハンドリング
     * 
     * @param ex    発生した例外
     * @param model Modelオブジェクト
     * @return エラーページのHTML
     */
    private String handleError(Exception ex, Model model) {
        return ErrorHandling.handleError(ex, model);
    }
}
