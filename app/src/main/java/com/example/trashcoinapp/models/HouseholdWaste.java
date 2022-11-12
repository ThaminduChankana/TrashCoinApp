package com.example.trashcoinapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class HouseholdWaste implements Serializable {
    String objectId;

    @Exclude
    private String userId;

    private String name, phone, address,houseWaste;


    public HouseholdWaste(){

    }
    public String getObjectId() {
        return objectId;
    }

    public HouseholdWaste(String objectId, String userId, String name, String phone, String address, String houseWaste) {
        this.objectId = objectId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.houseWaste = houseWaste;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHouseWaste() {
        return houseWaste;
    }

    public void setHouseWaste(String houseWaste) {
        this.houseWaste = houseWaste;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
