package com.safehouse.fizyoterapim.Firebase.Model;

import java.io.Serializable;

public class Form implements Serializable {
    private String userId;
    private String url;
    private String name;
    private boolean active;

    public Form() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
