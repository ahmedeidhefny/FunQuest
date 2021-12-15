package com.helloWorldTech.funQuest.ui.chat;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    private String content;

    @ServerTimestamp
    private Date created;

    private String from;
    private String to;
    private int from_id;
    private int to_id;
    private String mediaType;
    private String roomId;
    private String url;
    private String image;
    private boolean user_new;

    public Message(){

    }

    public Message(String content, Date created) {
        this.content = content;
        this.created = created;
    }

    public Message(String content, Date created, String from, String to, int from_id, int to_id, String mediaType, String roomId, String url, boolean user_new, String image) {
        this.content = content;
        this.created = created;
        this.from = from;
        this.to = to;
        this.from_id = from_id;
        this.to_id = to_id;
        this.mediaType = mediaType;
        this.roomId = roomId;
        this.url = url;
        this.user_new = user_new;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isNew() {
        return user_new;
    }

    public void setNew(boolean user_new) {
        this.user_new = user_new;
    }
}
