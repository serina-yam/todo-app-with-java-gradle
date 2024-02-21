package com.example.todoappwithjavagradle;

/**
 * 日付のフォーマットパターンを定義する列挙型
 */
public enum DateFormatPattern {
    /**
     * "yyyy-MM-dd"形式の日付パターン
     */
    YYYY_MM_DD("yyyy-MM-dd"),
    
    /**
     * "MM/dd/yyyy"形式の日付パターン
     */
    MM_DD_YYYY("MM/dd/yyyy");

    private final String pattern;
    
    /**
     * 指定されたパターンでDateFormatPatternを作成
     *
     * @param pattern 日付パターン
     */
    DateFormatPattern(String pattern) {
        this.pattern = pattern;
    }
    
    /**
     * 日付パターンを取得
     *
     * @return 日付パターン
     */
    public String getPattern() {
        return pattern;
    }
}
