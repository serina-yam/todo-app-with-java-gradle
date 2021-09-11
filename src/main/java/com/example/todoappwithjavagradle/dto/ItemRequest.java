package com.example.todoappwithjavagradle.dto;

import java.util.Date;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * アイテム情報 リクエストデータ
 */
@Data
public class ItemRequest implements Serializable {

  /**
   * タイトル
   */
  @NotEmpty(message = "名前を入力してください")
  @Size(max = 100, message = "名前は100桁以内で入力してください")
  private String title;

  /**
   * 期限
   */
  @Size(max = 255, message = "期限は今日以降の日付を入力してください")
  private Date timeLimit;

}
