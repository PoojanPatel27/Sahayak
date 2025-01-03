package com.example.myapplication.quickaid;

public class SosAlertModel {
    String name,contact,location,areaCode,uid;

    public SosAlertModel() {
    }

    public SosAlertModel(String name, String contact, String location, String areaCode, String uid) {
        this.name = name;
        this.contact = contact;
        this.location = location;
        this.areaCode = areaCode;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
