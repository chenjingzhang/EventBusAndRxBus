package com.dxt2.rxbuss;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class NewsModel {
    private int status;
    private String msg;

    public NewsModel(int status) {
        this.status = status;
    }

    public NewsModel(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
