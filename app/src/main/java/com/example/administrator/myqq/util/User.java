package com.example.administrator.myqq.util;

import com.example.administrator.myqq.Entity.BuddyEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/14.
 */
public class User implements Serializable {

    String account;
    String email;
    String password;
    String message;
    int avatar=0;
    String nick;
    String trends;

    String buddies;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String account) {
        this.email = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String userId) {
        account = userId;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTrends() {
        return trends;
    }

    public void setTrends(String trends) {
        this.trends = trends;
    }

    public String getBuddies() {
        return buddies;
    }

    public void setBuddies(String buddies) {
        this.buddies = buddies;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("message", message);
        result.put("avatar", avatar);
        result.put("nick", nick);
        result.put("trends", trends);
        result.put("buddies",buddies);
        return result;
    }

    public BuddyEntity toBuddyEntity() {
        BuddyEntity buddyEntity = new BuddyEntity();
        buddyEntity.setAccount(email.split("@")[0]);
        buddyEntity.setAvatar(avatar);
        buddyEntity.setNick(nick != null ? nick : "未命名");
        buddyEntity.setTrends(trends!=null ? trends:"你的朋友暂时没有新的动态哦~");
        buddyEntity.setMessage(message!=null? message:" ");

        return buddyEntity;

    }
}
