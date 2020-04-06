package com.javashitang.tool;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 注解的作用是序列化json时，如果是null对象，key也会消失 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse implements Serializable{

    private static final long serialVersionUID = 5079806402898956068L;

    private ServerResponse.Status status;
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
