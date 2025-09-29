package com.prouvetech.prouvetech.utils;

public class ResponseMessage {
    private boolean success;
    private String message;

    public ResponseMessage() {

    }

    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
