package com.khiladiadda.chat.model;


import java.util.List;

public class ChatMessage {

    private String id;
    private String text;
    private String photoUrl;
    private String senderName;
    private List<String> fcmToken;

    public ChatMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public List<String> getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(List<String> fcmToken) {
        this.fcmToken = fcmToken;
    }
}