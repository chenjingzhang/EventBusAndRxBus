package com.dxt2.eventbussdemo;

/**
 * Created by Administrator on 2018/5/2 0002.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
