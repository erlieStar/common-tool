package com.javashitang.tool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javashitang.tool.page.PageInfo;
import sun.jvm.hotspot.debugger.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 注解的作用是序列化json时，如果是null对象，key也会消失 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperStatus implements Serializable{

    private static final long serialVersionUID = 5079806402898956068L;

    private OperStatus.Status status;
    // 分页数据
    private PageInfo page;
    // 键值对
    private Map<String, Object> property;
    // 列表数据
    private List<Object> data;

    public OperStatus(GlobalStatus status) {
        this.status = new Status(status);
    }

    public OperStatus(GlobalStatus status, String msg) {
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

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public void setPageInfo(int curPage, int pageSize, int totalItem) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurPage(curPage);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalItem(totalItem);
        pageInfo.setTotalPage(pageSize, totalItem);
    }

    public static OperStatus newSuccess() {
        return new OperStatus(GlobalStatus.SUCCESS);
    }

    public static OperStatus newSuccess(String message) {
        return new OperStatus(GlobalStatus.SUCCESS, message);
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

    public static OperStatus newServerResponse(GlobalStatus status) {
        return new OperStatus(status);
    }

    public static OperStatus newServerResponse(GlobalStatus status, String message) {
        return new OperStatus(status, message);
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
