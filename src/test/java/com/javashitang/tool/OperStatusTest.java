package com.javashitang.tool;

import org.junit.Test;

import java.util.Arrays;

public class OperStatusTest {

    @Test
    public void demo1() {

        // 只返回状态信息
        OperStatus.newError();
        OperStatus.newError("这是自定义错误信息");

        // 在data属性中放列表数据
        OperStatus response = OperStatus.newError();
        response.setData(Arrays.asList(1, 2, 3));
        System.out.println(JsonConvert.obj2StrPretty(response));

    }

    @Test
    public void demo2() {
        // 在property属性中放key->value数据
        OperStatus response = OperStatus.newParamInvalid();
        response.setProperty("key", "value");
        System.out.println(JsonConvert.obj2StrPretty(response));
    }
}
