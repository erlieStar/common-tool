package com.javashitang.tool;

import java.io.Serializable;
import java.util.ArrayList;

/** 注解的作用是序列化json时，如果是null对象，key也会消失 */
public class OperStatus<T> implements Serializable {

    private static final long serialVersionUID = 5079806402898956068L;

    private String msg;

    private Integer code;

    private T data;

    public OperStatus() {}

    public OperStatus(GlobalStatus status) {
        this.code = status.value;
        this.msg = status.name;
    }

    public OperStatus(GlobalStatus status, String msg) {
        this.code = status.value;
        this.msg = msg;
    }

    public boolean isCodeEq(GlobalStatus compare) {
        return this.code.equals(compare.value);
    }

    public boolean isSuccess() {
        return this.code.equals(GlobalStatus.SUCCESS.value);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public static OperStatus newSuccess() {
        return new OperStatus(GlobalStatus.SUCCESS);
    }

    public static OperStatus newSuccess(String msg) {
        return new OperStatus(GlobalStatus.SUCCESS, msg);
    }

    public static <T> OperStatus<T> newSuccess(T data) {
        OperStatus<T> operStatus = new OperStatus<T>(GlobalStatus.SUCCESS);
        operStatus.setData(data);
        return operStatus;
    }

    public static OperStatus newError() {
        return new OperStatus(GlobalStatus.ERROR);
    }

    public static OperStatus newError(String message) {
        return new OperStatus(GlobalStatus.ERROR, message);
    }

    public static OperStatus newParamInvalid() {
        return new OperStatus(GlobalStatus.PARAM_INVALID);
    }

    public static OperStatus newParamInvalid(String message) {
        return new OperStatus(GlobalStatus.PARAM_INVALID, message);
    }

    public static OperStatus newResultEmpty() {
        OperStatus operStatus = new OperStatus(GlobalStatus.RESULT_EMPTY);
        operStatus.setData(new ArrayList<>());
        return operStatus;
    }

    public static OperStatus newResultEmpty(String message) {
        OperStatus operStatus = new OperStatus(GlobalStatus.RESULT_EMPTY);
        operStatus.setData(new ArrayList<>());
        return operStatus;
    }

    public static OperStatus newOperStatus(GlobalStatus status) {
        return new OperStatus(status);
    }

    public static OperStatus newOperStatus(GlobalStatus status, String message) {
        return new OperStatus(status, message);
    }
}
