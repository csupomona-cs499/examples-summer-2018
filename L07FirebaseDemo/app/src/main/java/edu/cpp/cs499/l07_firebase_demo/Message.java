package edu.cpp.cs499.l07_firebase_demo;

public class Message {

    public static final int TEXT = 1;
    public static final int IMAGE = 2;

    private long time;
    private String senderId;
    private String message;
    private int type;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
