package com.javashitang.tool;

import com.javashitang.tool.enums.PayType;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnumConvertTest {

    @Test
    public void convertToEnumType() {
        PayType payType = EnumConvert.convertToEnumType(PayType.class, 1);
        assertEquals(payType, PayType.ONLINE);

        payType = EnumConvert.convertToEnumType(PayType.class, 4);
        assertNull(payType);
    }

    @Test
    public void testConvertToEnumType() {
        PayType payType = EnumConvert.convertToEnumType(PayType.class, "线上");
        assertEquals(payType, PayType.ONLINE);

        payType = EnumConvert.convertToEnumType(PayType.class, "支付");
        assertNull(payType);
    }
}
