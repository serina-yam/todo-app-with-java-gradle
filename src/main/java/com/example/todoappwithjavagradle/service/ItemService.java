package com.example.todoappwithjavagradle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todoappwithjavagradle.dto.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;

/**
 * アイテム情報 Service
 */
@Service
public class ItemService {

    /**
    * アイテム情報取得
    */
    @Autowired
    ItemRepository itemRepository;

    public List<Item> searchAll() {
        // アイテムTBLの内容を全検索
        return itemRepository.findAll();
    }


      /**
   * アイテム情報 新規登録
   * @param item アイテム情報
   */
  public void create(ItemRequest ItemRequest) {
    // Date now = new Date();
    Item item = new Item();
    // idは自動付与

    item.setTitle(ItemRequest.getTitle());
    item.setDoneFlg(0); //済フラグは最初は0
    item.setTimeLimit(ItemRequest.getTimeLimit());

    // item.setCreateDate(now);
    // item.setUpdateDate(now);
    itemRepository.save(item);
  }
}
