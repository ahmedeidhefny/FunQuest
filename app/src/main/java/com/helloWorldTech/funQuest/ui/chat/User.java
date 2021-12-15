package com.helloWorldTech.funQuest.ui.chat;

public class User {
    private String name;
    private String new1;
    private String updated;
    private int user_id;

    public User(String name,String new1, String updated, int user_id) {
        this.name = name;
        this.updated = updated;
        this.user_id = user_id;
        this.new1 = new1 ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
