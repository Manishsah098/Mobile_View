package com.example.atlantis.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String id;
    private String text;
    private String sender;
    private String timestamp;
    private boolean isAi;

    public ChatMessage() {
    }

    public ChatMessage(String id, String text, String sender, String timestamp, boolean isAi) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
        this.isAi = isAi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }
}
