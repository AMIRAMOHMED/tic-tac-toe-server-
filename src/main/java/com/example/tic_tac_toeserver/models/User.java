package com.example.tic_tac_toeserver.models;
import org.json.JSONObject;

public class User {
    private int userid;
    private String username;
    private String email;
    private String password;

    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(int userid, String username, String email, String password) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"userid\":" + userid + ", \"username\":\"" + username + "\", \"email\":\"" + email + "\", \"password\":\"" + password
                + "\"}";
    }

    public static User toUser(String userString){
        return toUser(new JSONObject(userString));
    }

    public static User toUser(JSONObject user){
        int  userid = (int) getValue(user, "userid", 0);
        String username = (String) getValue(user, "username");
        String email = (String) getValue(user, "email");
        String password = (String) getValue(user, "password");
        return new User(userid,username,email,password);
    }

    private static Object getValue(JSONObject obj, String str, Object defaultValue) {
        try {
            return obj.get(str);
        }catch(Exception e){
            return defaultValue;
        }
    }
    private static Object getValue(JSONObject obj, String str) {
       return getValue(obj, str, null);
    }
}
