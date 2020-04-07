package com.javashitang.tool.async;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AsyncUtilTest {

    @Test
    public void commit() throws InterruptedException {
        AsyncUtil.getInstance().commit(arg -> {
            System.out.println("aaa");
            throw new RuntimeException();
        });
        TimeUnit.SECONDS.sleep(4);
    }
}
