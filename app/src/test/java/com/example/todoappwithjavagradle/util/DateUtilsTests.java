package com.example.todoappwithjavagradle.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtilsTests {

    @Test
    public void testParseDate() throws ParseException {
        // テストデータ
        String dateString = "2024-12-11";
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.set(2024, Calendar.DECEMBER, 11, 0, 0, 0); // 時刻部分をすべて0に設定
        expectedCalendar.set(Calendar.MILLISECOND, 0);
        Date expectedDate = expectedCalendar.getTime();
        
        // テスト
        Date parsedDate = DateUtils.parseDate(dateString);

        // 検証
        assertEquals(expectedDate, parsedDate);
    }
}
