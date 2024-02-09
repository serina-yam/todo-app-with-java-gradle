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
    // Date now = new Date();
    Item item = new Item();
    // idは自動付与

    // 日付変換
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = simpleDateFormat.format(ItemRequest.getTimeLimit());
    java.sql.Date date = java.sql.Date.valueOf(formattedDate);

    item.setTitle(ItemRequest.getTitle());
    item.setDoneFlg(0); //済フラグは最初は0
    item.setTimeLimit(date);

    // item.setCreateDate(now);
    // item.setUpdateDate(now);
    itemRepository.save(item);
  }


  // 削除
  public void delete(Integer id) {
    itemRepository.deleteById(id);
  }

}
