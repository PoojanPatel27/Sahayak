package com.example.myapplication.quickaid;

public class UserHistroyModel {
    private String Uid,name,contact,location,problem,msgUid;

    public UserHistroyModel(String uid, String name, String contact, String location, String problem, String msgUid) {
        Uid = uid;
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.problem = problem;
        this.msgUid = msgUid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(String msgUid) {
        this.msgUid = msgUid;
    }
}
