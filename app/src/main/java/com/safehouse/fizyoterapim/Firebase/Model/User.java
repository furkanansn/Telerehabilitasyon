package com.safehouse.fizyoterapim.Firebase.Model;

public class User {

    private String id;
    private String name;
    private String birthDay;
    private String email;
    private String phone;
    private String TC;
    private String issues;


    public User() {
    }

    public User(String id, String name, String birthDay, String email, String phone, String TC, String issues) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.email = email;
        this.phone = phone;
        this.TC = TC;
        this.issues = issues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }
}
