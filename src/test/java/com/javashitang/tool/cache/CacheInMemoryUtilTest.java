package com.javashitang.tool.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheInMemoryUtilTest {

    @Test
    public void put() {
        CacheInMemoryUtil.put("test", "aaa");
        String value = CacheInMemoryUtil.get("test", String.class);
        assertEquals(value, "aaa");
    }
}
