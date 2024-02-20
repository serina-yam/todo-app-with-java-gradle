package com.example.todoappwithjavagradle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * テスト用のユーティリティクラス
 */
public class TestUtil {

    /**
     * 文字列をjava.util.Dateに変換するメソッド
     *
     * @param dateString 変換する文字列
     * @param pattern    文字列の形式を定義するenum
     * @return 変換されたjava.util.Dateオブジェクト
     * @throws ParseException 文字列の形式が正しくない場合にスローされる例外
     */
    public static Date stringToDate(String dateString, DateFormatPattern pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.getPattern());
        return dateFormat.parse(dateString);
    }
}
