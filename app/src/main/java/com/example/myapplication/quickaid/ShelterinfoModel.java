package com.example.myapplication.quickaid;

public class ShelterinfoModel {
    String name,contact,capactiy,location,shelterUid;

    public ShelterinfoModel() {
    }

    public ShelterinfoModel(String name, String contact, String capactiy, String location, String shelterUid) {
        this.name = name;
        this.contact = contact;
        this.capactiy = capactiy;
        this.location = location;
        this.shelterUid = shelterUid;
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

    public String getCapactiy() {
        return capactiy;
    }

    public void setCapactiy(String capactiy) {
        this.capactiy = capactiy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShelterUid() {
        return shelterUid;
    }

    public void setShelterUid(String shelterUid) {
        this.shelterUid = shelterUid;
    }
}
