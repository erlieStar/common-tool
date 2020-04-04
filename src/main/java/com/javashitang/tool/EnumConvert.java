package com.javashitang.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class EnumConvert {

    private static final Logger logger = LoggerFactory.getLogger(EnumConvert.class);

    /**
     * 根据value字段的值转为枚举类型
     */
    public static <T extends Enum<T>> T convertToEnumType(Class<T> enumType, Integer value) {
        if (value == null) {
            return null;
        }
        try {
            T[] allEnums = enumType.getEnumConstants();
            if (allEnums == null || allEnums.length == 0) {
                return null;
            }
            Field field = enumType.getDeclaredField("value");
            for (T t : allEnums) {
                if (value == field.getInt(t)) {
                    return t;
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("failed convert value to type, value is {}", value, e);
            return null;
        }
    }

    /**
     * 根据name字段的值转为枚举类型
     */
    public static <T extends Enum<T>> T convertToEnumType(Class<T> enumType, String name) {
        if (name == null) {
            return null;
        }
        try {
            T[] allEnums = enumType.getEnumConstants();
            if (allEnums == null || allEnums.length == 0) {
                return null;
            }
            Field field = enumType.getDeclaredField("name");
            for (T t : allEnums) {
                if (name.equals(field.get(t))) {
                    return t;
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("failed convert value to type, name is {}", name, e);
            return null;
        }
    }
}
