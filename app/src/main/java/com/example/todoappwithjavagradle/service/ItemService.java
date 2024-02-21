package com.example.todoappwithjavagradle.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todoappwithjavagradle.Form.ItemRequest;
import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;
import com.example.todoappwithjavagradle.util.AttributeKey;

import jakarta.servlet.http.HttpSession;

/**
 * アイテム情報を操作するサービスクラス
 */
@Service
public class ItemService {

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  private HttpSession httpSession;

  /**
   * 特定のユーザーに関連するすべてのアイテム情報を取得
   *
   * @param userId ユーザーID
   * @return アイテム情報のリスト
   */
  public List<Item> searchAll(Integer userId) {
    // アイテムTBLの内容を全検索
    return itemRepository.findByUserIdOrderByCreatedAtAsc(userId);
  }

  /**
   * 新しいアイテム情報を登録
   *
   * @param itemRequest 登録するアイテム情報のリクエスト
   */
  public void create(ItemRequest ItemRequest) {

    Item item = new Item();
    Integer userId = Integer.parseInt(httpSession.getAttribute(AttributeKey.USER_ID.getValue()).toString());

    // 日付変換
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = simpleDateFormat.format(ItemRequest.getTimeLimit());
    java.sql.Date date = java.sql.Date.valueOf(formattedDate);

    item.setUserId(userId);
    item.setTitle(ItemRequest.getTitle());
    item.setState(0);
    item.setTimeLimit(date);

    itemRepository.save(item);
  }

  /**
   * 指定されたIDのアイテムの状態を更新する
   *
   * @param id    更新対象のアイテムID
   * @param state 更新する状態
   */
  public void update(Integer id, Integer state) {
    itemRepository.updateStateById(id, state);
  }

  /**
   * 指定されたIDのアイテム情報を削除
   *
   * @param id 削除するアイテムのID
   */
  public void delete(Integer id) {
    if (id == null) {
      return;
    }
    itemRepository.deleteById(id);
  }
}
