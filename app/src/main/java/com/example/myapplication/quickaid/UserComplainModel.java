package com.example.myapplication.quickaid;

public class UserComplainModel {
    String name,contact,problem,location,experties,Status,uid;

    public UserComplainModel() {
    }

    public UserComplainModel(String name, String contact, String problem, String location, String experties, String status, String uid) {
        this.name = name;
        this.contact = contact;
        this.problem = problem;
        this.location = location;
        this.experties = experties;
        Status = status;
        this.uid = uid;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperties() {
        return experties;
    }

    public void setExperties(String experties) {
        this.experties = experties;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
