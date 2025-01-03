package com.example.myapplication.quickaid;

public class OrgDetailsModel {
    String name,number,category,email,address,pincode,orgUid;

    public OrgDetailsModel() {
    }


    public OrgDetailsModel(String name, String number, String category, String email, String address, String pincode, String orgUid) {
        this.name = name;
        this.number = number;
        this.category = category;
        this.email = email;
        this.address = address;
        this.pincode = pincode;
        this.orgUid = orgUid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgUid() {
        return orgUid;
    }

    public void setOrgUid(String orgUid) {
        this.orgUid = orgUid;
    }
}
