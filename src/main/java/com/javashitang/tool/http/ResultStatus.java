package com.javashitang.tool.http;

public class ResultStatus {
    private boolean success;
    private boolean isException;
    private int httpCode;
    private String strResponse;
    private byte[] byteResponse;
    private String exception;
    private long costTime;
    private long startTime;

    public ResultStatus(int httpCode, String response) {
        this.httpCode = httpCode;
        this.success = httpCode >= 200 && httpCode < 300;
        this.strResponse = response;
    }

    public ResultStatus() {
    }

    public boolean isSuccess() {
        return this.success;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public String getStrResponse() {
        return this.strResponse;
    }

    public long getCostTime() {
        return this.costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    protected ResultStatus setCode(int code) {
        this.httpCode = code;
        this.success = this.httpCode >= 200 && this.httpCode < 300;
        return this;
    }

    protected ResultStatus setResponse(String response) {
        this.strResponse = response;
        return this;
    }

    protected ResultStatus setException(String exception) {
        this.strResponse = null;
        this.isException = true;
        this.exception = exception;
        return this;
    }

    public boolean isException() {
        return this.isException;
    }

    public String getException() {
        return this.exception;
    }

    public String toString() {
        return "http client result: [" + "isSuccess=" + this.success + ", httpCode=" + this.httpCode + ", exception=" + this.exception + ", isException=" + this.isException + ", response=" + (!this.isSuccess() ? this.strResponse : "") + "]" + ", costTime=[" + this.costTime + "]";
    }

    public byte[] getByteResponse() {
        return this.byteResponse;
    }

    public void setByteResponse(byte[] byteResponse) {
        this.byteResponse = byteResponse;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}



