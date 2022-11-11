package com.example.trashcoinapp.models;

public class Order {

    String orderNo;
    String userId;
    String name;
    String contactNo;
    String address;
    float price;
    String productList;
    String status;

    public Order(){

    }

    public Order(String orderNo, String userId, String name, String contactNo, String address, float price, String productList, String status) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
        this.price = price;
        this.productList = productList;
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductList() {
        return productList;
    }

    public void setProductList(String productList) {
        this.productList = productList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
