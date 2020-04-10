package com.javashitang.tool.http;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class HttpUtilTest {

    @Test
    public void httpGet() {
        ResultStatus resultStatus = HttpUtil.getInstance().httpGet("http://www.baidu.com");
        if (resultStatus.isSuccess()) {
            System.out.println(resultStatus.getStrResponse());
        }
    }
}
