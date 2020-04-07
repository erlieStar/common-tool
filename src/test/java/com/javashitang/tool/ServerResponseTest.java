package com.javashitang.tool;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ServerResponseTest {

    @Test
    public void demo1() {

        // 只返回状态信息
        ServerResponse.newError();
        ServerResponse.newError("这是自定义错误信息");

        // 在data属性中放列表数据
        ServerResponse response = ServerResponse.newSuccess();
        response.setData(Arrays.asList(1, 2, 3));

    }

    public void demo2() {
        // 在property属性中放key->value数据
        ServerResponse response = ServerResponse.newSuccess();
        response.setProperty("key", "value");
    }
}
