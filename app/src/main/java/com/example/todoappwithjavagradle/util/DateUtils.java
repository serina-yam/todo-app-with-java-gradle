package com.example.todoappwithjavagradle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日付を解析するユーティリティクラス
 */
public class DateUtils {

    /**
     * 文字列形式の日付を解析してDateオブジェクトに変換する
     *
     * @param dateString 解析する日付の文字列
     * @return 解析されたDateオブジェクト
     * @throws ParseException 解析中にエラーが発生した場合
     */
    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateString);
    }
}
