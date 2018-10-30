package com.visium.fieldservice.entity;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class AuthToken {

    private static final long serialVersionUID = -6814349142355257663L;

    private String token;
    private int expiry;
    private long expiryTime;

    public AuthToken(){}

    public AuthToken(String token, int expiry) {
        this.token = token;
        this.expiry = expiry;
        this.expiryTime = System.currentTimeMillis() + (expiry * 1000);
    }

    public boolean isExpired() {
        return false/*System.currentTimeMillis() >= expiryTime*/;
    }

    public boolean exists() {
        return this.token != null /*&& this.expiry > 0*/;
    }

    public String get() {
        return this.token;
    }
}
