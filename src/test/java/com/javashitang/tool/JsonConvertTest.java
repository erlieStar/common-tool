package com.javashitang.tool;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class JsonConvertTest {

    @Data
    @NoArgsConstructor
    public class Student {
        private String name;
        private Integer age;
    }

    @Test
    public void obj2Str() {
    }

    @Test
    public void str2Obj() {
        HashMap<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "10");
        map.put("age", 10);
    }
}