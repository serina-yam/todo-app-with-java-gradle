package com.example.todoappwithjavagradle.dto;

import java.util.Date;

import java.io.Serializable;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * アイテム情報 リクエストデータ
 */
@Data
public class ItemRequest implements Serializable {

  /**
   * タイトル
   */
  @NotEmpty(message = "タイトルを入力してください")
  @Size(max = 100, message = "タイトルは100桁以内で入力してください")
  private String title;

  /**
   * 期限
   */
  @NotNull(message = "期限を入力してください")
  // @Size(max = 255, message = "期限は今日以降の日付を入力してください")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date timeLimit;

}
