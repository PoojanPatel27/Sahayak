package com.example.myapplication.quickaid;

public class MessageModel {

    private String userUid,name,contact,location,status,messageUid, problem;

    public MessageModel() {
    }


    public MessageModel(String userUid, String name, String contact, String location, String status, String messageUid, String problem) {
        this.userUid = userUid;
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.status = status;
        this.messageUid = messageUid;
        this.problem = problem;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
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

    public String getMessageUid() {
        return messageUid;
    }

    public void setMessageUid(String messageUid) {
        this.messageUid = messageUid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
