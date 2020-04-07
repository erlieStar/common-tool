package com.javashitang.tool;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    private static final Logger logger = LoggerFactory.getLogger(DateHelper.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 将日期字符串转为LocalDateTime
     */
    public static LocalDateTime str2DateTime(String dateStr, String format) {
        if (StringUtils.isAnyBlank(dateStr, format)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateStr, formatter);
    }

    /**
     * 将日期字符串转为LocalDate
     */
    public static LocalDate str2Date(String dateStr, String format) {
        if (StringUtils.isAnyBlank(dateStr, format)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * 将LocalDateTime转为日期字符串
     */
    public static String dateTime2Str(LocalDateTime localDateTime, String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDateTime);
    }


    /**
     * 将LocalDate转为日期字符串
     */
    public static String date2Str(LocalDate localDate, String format) {
        if (StringUtils.isBlank(format)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    /**
     * 时间戳转LocalDate
     */
    public static LocalDate timestamp2Date(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }


    /**
     * 时间戳转LocalDateTime
     */
    public static LocalDateTime timestamp2DateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * LocalDate转时间戳
     */
    public static Long date2Timestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }


    /**
     * LocalDateTime转时间戳
     */
    public static Long dateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

}
