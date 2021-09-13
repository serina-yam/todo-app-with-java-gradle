package com.example.todoappwithjavagradle.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * ユーザー情報 Entity
 */
@Entity
@Data
@Table(name = "todo_items")
public class Item {

    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * title
     */
    @Column(name = "title")
    private String title;


    /**
     * done_flg
     */
    @Column(name = "done_flg")
    private Integer doneFlg;


    /**
     * time_limit
     */
    @Column(name = "time_limit")
    private  Date timeLimit;

}
