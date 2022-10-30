package com.example.trashcoinapp.models;

public class Cart {
    String userID;
    String id;
    String productName;
    float price;
    float totalPrice;
    float discount;
    float quantity;


    public Cart(String userID, String id, String productName, float price, float totalPrice, float discount, float quantity) {
        this.userID = userID;
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.quantity = quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
