package com.example.todoappwithjavagradle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoappwithjavagradle.entity.Item;

/**
 * アイテム情報にアクセスするためのリポジトリインターフェース
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>  {

    /**
     * 指定されたユーザーIDに関連するすべてのアイテム情報を取得
     *
     * @param userId ユーザーID
     * @return ユーザーに関連するアイテム情報のリスト
     */
    List<Item> findByUserId(Integer userId);
}
