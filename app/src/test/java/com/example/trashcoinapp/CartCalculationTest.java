package com.example.trashcoinapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.trashcoinapp.activities.cart.CartCalculation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartCalculationTest {
    private CartCalculation cartCalculation;
    @BeforeEach
    public void setup(){
        cartCalculation=new CartCalculation();
    }
    @Test
    public void testDecreaseTotalPrice(){
        double result=cartCalculation.decreaseTotalPrice(5.0F,100.0F);
        assertEquals(400.0,result);
    }
    @Test
    public void testIncreaseTotalPrice(){
        double result=cartCalculation.increaseTotalPrice(5.0F,100.0F);
        assertEquals(600.0,result);
    }
}
