package com.example.todoappwithjavagradle.entity;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * アイテム情報エンティティクラス
 */
@Entity
@Data
@Table(name = "item")
public class Item {

    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "state")
    private Integer state;

    @Column(name = "time_limit")
    private Date timeLimit;

    @Column(name = "created_at")
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at")
    private java.sql.Timestamp updatedAt;

    public Item() {
    }

    /**
     * コンストラクタ
     * 
     * @param id        アイテムID
     * @param userId    ユーザーID
     * @param title     タイトル
     * @param state     状態
     * @param timeLimit 期限
     * @param createdAt 作成日時
     * @param updatedAt 更新日時
     */
    public Item(Integer id, Integer userId, String title, Integer state, Date timeLimit, Timestamp createdAt,
            Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.state = state;
        this.timeLimit = timeLimit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * エンティティが作成されたときの処理
     */
    @PrePersist
    protected void onCreate() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.createdAt = ts;
        this.updatedAt = ts;
    }

    /**
     * エンティティが更新されたときの処理
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
