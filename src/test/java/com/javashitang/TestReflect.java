package com.javashitang;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestReflect {

    // 反射生成类并且调用类的方法
    @Test
    public void test1() throws Exception {

        Class<?> clazz  = Class.forName("com.javashitang.domain.User");
        Constructor constructor= clazz.getConstructor(String.class, String.class);
        Object classObject = constructor.newInstance("zhansan", "123");
        // User(username=zhansan, password=123)
        System.out.println(classObject);

        Method method = clazz.getMethod("getPassword");
        Object result = method.invoke(classObject);
        // 123
        System.out.println(result);
    }

}
