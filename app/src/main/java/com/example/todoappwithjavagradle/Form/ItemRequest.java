package com.example.todoappwithjavagradle.Form;

import java.util.Date;

import java.io.Serializable;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * アイテム情報のリクエストデータを表すフォームクラス
 */
@Data
public class ItemRequest implements Serializable {

  /**
   * アイテムのタイトル
   */
  @NotEmpty(message = "タイトルを入力してください")
  @Size(max = 100, message = "タイトルは100桁以内で入力してください")
  private String title;

  /**
   * アイテムの期限
   */
  @NotNull(message = "期限を入力してください")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date timeLimit;

}
