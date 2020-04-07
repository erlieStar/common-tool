package com.javashitang.tool.enums;

import com.sun.tools.classfile.ConstantPool;

public enum PayType {

    ONLINE(1, "线上"),
    OFFILINE(2, "线下");

    private final int value;
    private final String name;

    PayType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
