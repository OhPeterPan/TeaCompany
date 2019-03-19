package com.example.administrator.chadaodiancompany.event;

public class MessageEvent {
    public final String message;
    public final int code;

    public MessageEvent(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
