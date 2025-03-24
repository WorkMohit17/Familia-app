package com.example.familia.chat.model;

public class Message {
    private String text;
    private boolean isUser;  // true = user message, false = bot message

    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }
}

