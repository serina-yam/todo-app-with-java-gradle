package com.example.todoappwithjavagradle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.annotation.Validated;

import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.service.ItemService;

/**
 * アイテム情報 Controller
 */
@Controller
public class ItemController {

  /**
   * アイテム情報 Service
   */
  @Autowired
  ItemService itemService;

  /**
  * アイテム情報一覧画面を表示
  * @param model Model
  * @return アイテム情報一覧画面のHTML
  */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String displayList(Model model) {
    List<Item> itemlist = itemService.searchAll();
    model.addAttribute("itemlist", itemlist);
    return "list";
  }

  /**
   * アイテム新規登録
   * @param userRequest リクエストデータ
   * @param model Model
   * @return アイテム情報一覧画面
   */
  @RequestMapping(value = "/list/create", method = RequestMethod.POST)
  public String create(@Validated ItemRequest itemRequest, BindingResult result, Model model) {
    if (result.hasErrors()) {
      // 入力チェックエラーの場合
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }
      model.addAttribute("validationError", errorList);
      return "/list/create";
    }
    // アイテム情報の登録
    itemService.create(itemRequest);
    return "redirect:/list";
  }
}