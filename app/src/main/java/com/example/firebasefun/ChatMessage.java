package com.example.firebasefun;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.UUID;

@IgnoreExtraProperties
public class ChatMessage {

    public String message;
    public long timestamp;
    public String uuid;

    public ChatMessage() {
    }

    public ChatMessage(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.uuid = UUID.randomUUID().toString();
    }
}
