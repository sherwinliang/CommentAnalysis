package com.ca.bean;

import java.util.List;

public class RespBean {
    private String status;
    private String msg;
    private List<?> data;

    public RespBean() {
    }

    public RespBean(String status, String msg) {

        this.status = status;
        this.msg = msg;
    }

    public RespBean(String status, String msg, List<?> data) {

        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
