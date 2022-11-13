package com.example.trashcoinapp.activities.cart;

public class CartCalculation {

    public float decreaseTotalPrice(float qty, float price){
        float answer;
        answer=(qty-1) * price;
        return answer;
    }

    public float increaseTotalPrice(float qty, float price){
        float answer;
        answer=(qty+1) * price;
        return answer;
    }

}
