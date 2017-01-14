/**
 * ss好友实体类
 */
package com.example.administrator.myqq.Entity;

import java.io.Serializable;

public class BuddyEntity implements Serializable{
    private int avatar;
    private String account;
    private String nick;
    private String trends;
    private String message;

    public BuddyEntity() {

    }

    public BuddyEntity(int avatar, String account, String nick, String trends) {
        this.avatar = avatar;
        this.account = account;
        this.nick = nick;
        this.trends = trends;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
