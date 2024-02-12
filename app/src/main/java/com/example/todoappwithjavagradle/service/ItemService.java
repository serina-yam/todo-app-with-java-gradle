package com.example.todoappwithjavagradle.service;


import java.text.SimpleDateFormat;
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

    Item item = new Item();

    // 日付変換
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = simpleDateFormat.format(ItemRequest.getTimeLimit());
    java.sql.Date date = java.sql.Date.valueOf(formattedDate);

    item.setTitle(ItemRequest.getTitle());
    item.setState(0);
    item.setTimeLimit(date);

    itemRepository.save(item);
  }


  /**
   * 削除.
   * @param id
   */
  public void delete(Integer id) {
    itemRepository.deleteById(id);
  }

}
