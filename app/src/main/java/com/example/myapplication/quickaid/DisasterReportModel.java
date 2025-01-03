package com.example.myapplication.quickaid;

public class DisasterReportModel {

    String location,contact,description,imageUrl;

    public DisasterReportModel() {
    }

    public DisasterReportModel(String location, String contact, String description, String imageUrl) {
        this.location = location;
        this.contact = contact;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
