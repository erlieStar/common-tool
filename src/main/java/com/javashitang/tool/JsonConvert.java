package com.javashitang.tool;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonConvert {

    private static final Logger logger = LoggerFactory.getLogger(JsonConvert.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 反序列化时忽略没有的字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转为string
     */
    public static <T> String obj2Str(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("parse object to string error", e);
            return null;
        }
    }

    /**
     * 将对象转为string，格式化输出
     */
    public static <T> String obj2StrPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.warn("parse object to string error", e);
            return null;
        }
    }

    /**
     * 将string转为对象
     */
    public static <T> T str2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            logger.error("parse string to object error", e);
            return null;
        }
    }

    /**
     * 将string转为对象数组
     */
    public static <T> List<T> str2List(String str, Class<T> clazz) {
        JavaType t  = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return objectMapper.readValue(str, t);
        } catch (Exception e) {
            logger.error("parse string to list error", e);
            return null;
        }
    }
}
