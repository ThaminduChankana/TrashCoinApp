package com.example.trashcoinapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.trashcoinapp.activities.calculators.Calculation;
import com.example.trashcoinapp.activities.calculators.CollectorCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectorCalculatorTest {
    private Calculation calculation;
    @BeforeEach
    public void setup(){
        calculation=new  Calculation();
    }
    @Test
    public void testAdd(){
        int result=calculation.add(100,200);
        assertEquals(300,result);
    }

    @Test
    public void testSubtract(){
        int result=calculation.subtract(200,100);
        assertEquals(100,result);
    }

    @Test
    public void testMultiply(){
        int result=calculation.multiply(10,20);
        assertEquals(200,result);
    }

    @Test
    public void testDivide(){
        int result=calculation.divide(200,100);
        assertEquals(2,result);
    }

}
