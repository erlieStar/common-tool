package com.javashitang.tool;

public enum GlobalStatus {

    SUCCESS(0, "操作成功"),
    FAILED(1, "操作失败"),
    ERROR(2, "服务器出现错误"),
    RESULT_EMPTY(3, "结果为空"),
    PARAM_INVALID(4, "请求参数非法");

    private final int value;
    private final String name;

    private GlobalStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
