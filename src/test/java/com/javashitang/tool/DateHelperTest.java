package com.javashitang.tool;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateHelperTest {

    @Test
    public void str2DateTime() {
        LocalDateTime localDateTime = DateHelper.str2DateTime("2020-10-10 11:11:11", "yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime);
    }

    @Test
    public void str2Date() {
        LocalDate localDate = DateHelper.str2Date("2020-10-10", "yyyy-MM-dd");
        System.out.println(localDate);
    }

    @Test
    public void dateTime2Str() {
    }

    @Test
    public void date2Str() {
    }

    public static class domain {
    }
}
