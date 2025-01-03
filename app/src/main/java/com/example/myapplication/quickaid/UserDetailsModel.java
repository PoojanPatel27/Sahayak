package com.example.myapplication.quickaid;

public class UserDetailsModel {

    String name,number,email,uid;


    public UserDetailsModel() {
    }

    public UserDetailsModel(String name, String number, String email, String uid) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
