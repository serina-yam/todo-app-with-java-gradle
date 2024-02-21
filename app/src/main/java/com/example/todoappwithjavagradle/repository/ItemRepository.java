package com.example.todoappwithjavagradle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.todoappwithjavagradle.entity.Item;

/**
 * アイテム情報にアクセスするためのリポジトリインターフェース
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    /**
     * 指定されたユーザーIDに関連するすべてのアイテム情報を取得
     *
     * @param userId ユーザーID
     * @return ユーザーに関連するアイテム情報のリスト
     */
    List<Item> findByUserIdOrderByCreatedAtAsc(Integer userId);

    /**
     * 指定されたIDのアイテムの状態を更新する
     *
     * @param id    更新するアイテムのID
     * @param state 新しい状態
     */
    @Transactional
    @Modifying
    @Query("UPDATE Item i SET i.state = :state WHERE i.id = :itemId")
    void updateStateById(@Param("itemId") Integer id, @Param("state") Integer state);

}
