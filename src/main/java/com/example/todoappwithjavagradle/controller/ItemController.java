package com.example.todoappwithjavagradle.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;

import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;
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
  @GetMapping(value = "/item/list")
  public String displayList(Model model) {
    List<Item> itemlist = itemService.searchAll();
    model.addAttribute("itemlist", itemlist);
    return "/item/list";
  }

    /**
    * アイテム新規登録画面を表示
    * @param model Model
    * @return アイテム新規登録画面のHTML
    */
  @GetMapping(value = "/item/add")
  public String displayAdd(Model model) {
    model.addAttribute("itemRequest", new ItemRequest());
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
      // 入力チェックエラーの場合
      List<String> errorList = new ArrayList<String>();
      for (ObjectError error : result.getAllErrors()) {
        errorList.add(error.getDefaultMessage());
      }
      model.addAttribute("validationError", errorList);
      return "/item/add";
    }
    // アイテム情報の登録
    itemService.create(itemRequest);
    return "redirect:/item/list";
  }

    /**
   * アイテム情報 削除
   * @param itemRequest リクエストデータ
   * @param model Model
   * @return アイテム情報一覧画面
   */
  @PostMapping(value = "/item/delete")
  public String delete(@RequestParam Integer id) {
    itemService.delete(id);
    return "redirect:/item/list";
  }
}