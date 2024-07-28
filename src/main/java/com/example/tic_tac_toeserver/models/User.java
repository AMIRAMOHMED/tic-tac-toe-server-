package com.example.tic_tac_toeserver.models;

import org.json.JSONObject;

public class User {
    private String username;
    private String password;
    private boolean isOnline;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void fromJson(String json){
        JSONObject object = new JSONObject(json);
        password = object.getString("password");
        username = object.getString("username");
        isOnline = object.optBoolean("isOnline", false);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}

