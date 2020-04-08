package com.javashitang.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javashitang.tool.page.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 注解的作用是序列化json时，如果是null对象，key也会消失 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse implements Serializable{

    private static final long serialVersionUID = 5079806402898956068L;

    private ServerResponse.Status status;
    private PageInfo pageInfo;
    private Map<String, Object> property;
    private List<Object> data;

    public ServerResponse(GlobalStatus status) {
        this.status = new Status(status);
    }

    public ServerResponse(GlobalStatus status, String msg) {
        this.status = new Status(status, msg);
    }

    public boolean isSuccess() {
        return this.status.compare(GlobalStatus.SUCCESS);
    }

    public Map<String, Object> getProperty() {
        return this.property;
    }

    public void setProperty(String key, Object object) {
        if (this.property == null) {
            this.property = new HashMap<>();
        }
        this.property.put(key, object);
    }

    public void setProperty(Map<String, Object> property) {
        this.property = property;
    }

    public List<Object> getData() {
        return this.data;
    }

    public <T> List<T> getData(Class<T> clazz) {
        return (List)data;
    }

    public <T> void setData(List<T> data) {
        this.data = (List)data;
    }

    public void addObject(Object object) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
    }

    public static ServerResponse newSuccess() {
        return new ServerResponse(GlobalStatus.SUCCESS);
    }

    public static ServerResponse newSuccess(String message) {
        return new ServerResponse(GlobalStatus.SUCCESS, message);
    }

    public static ServerResponse newError() {
        return new ServerResponse(GlobalStatus.ERROR);
    }

    public static ServerResponse newError(String message) {
        return new ServerResponse(GlobalStatus.ERROR, message);
    }

    public static ServerResponse newParamInvalid() {
        return new ServerResponse(GlobalStatus.PARAM_INVALID);
    }

    public static ServerResponse newParamInvalid(String message) {
        return new ServerResponse(GlobalStatus.PARAM_INVALID, message);
    }

    public static ServerResponse newServerResponse(GlobalStatus status) {
        return new ServerResponse(status);
    }

    public static ServerResponse newServerResponse(GlobalStatus status, String message) {
        return new ServerResponse(status, message);
    }

    public class Status implements Serializable {

        private static final long serialVersionUID = 5079806402898956068L;

        private GlobalStatus status;
        private String desc;

        public Status (GlobalStatus status) {
            this.status = status;
            this.desc = status.name();
        }

        public Status (GlobalStatus status, String message) {
            this.status = status;
            this.desc = message;
        }

        public boolean compare(GlobalStatus input) {
            return this.status == input;
        }
    }

}
