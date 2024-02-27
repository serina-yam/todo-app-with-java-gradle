package com.example.todoappwithjavagradle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.todoappwithjavagradle.entity.User;

/**
 * ユーザーエンティティにアクセスするためのリポジトリインターフェース
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 指定されたユーザー名に対応するユーザーエンティティを取得
     *
     * @param username ユーザー名
     * @return ユーザーエンティティ
     */
    User findByUsername(String username);

    /**
     * 指定されたOAuth2ユーザーIDに対応するユーザーエンティティを取得
     *
     * @param oauth2UserId OAuth2ユーザーID
     * @return ユーザーエンティティ
     */
    User findByOauth2UserId(String oauth2UserId);

    /**
     * シーケンス取得
     * 
     * @return ユーザーID
     */
    @Query(value = "SELECT nextval('users_id_seq')", nativeQuery = true)
    Integer getNextUserId();
}
