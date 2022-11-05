package com.example.trashcoinapp.models;

public class Product {
    private String userId, title, description, category, picURL, discountNote;
    private float price, discountPrice,quantity;

    public Product(){

    }

    public Product(String userId, String title, String description, String category, float quantity, String picURL, String discountNote, float price, float discountPrice) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.picURL = picURL;
        this.discountNote = discountNote;
        this.price = price;
        this.discountPrice = discountPrice;
    }



    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getPicURL() {
        return picURL;
    }

    public String getDiscountNote() {
        return discountNote;
    }

    public float getPrice() {
        return price;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public void setDiscountNote(String discountNote) {
        this.discountNote = discountNote;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }
}
