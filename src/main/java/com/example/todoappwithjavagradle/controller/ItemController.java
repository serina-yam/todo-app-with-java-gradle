package com.example.todoappwithjavagradle.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
  * @return ユーザー情報一覧画面のHTML
  */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String displayList(Model model) {
    List<Item> itemlist = itemService.searchAll();
    model.addAttribute("itemlist", itemlist);
    return "list";
  }
}
