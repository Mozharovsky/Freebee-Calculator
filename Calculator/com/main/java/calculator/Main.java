package com.main.java.calculator;

import com.main.java.calculator.Model.QuadraticEquation;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class Main {
    public static void main(String[] args) {
        String s = "1x^2 - 20x - 69 = 0";
        QuadraticEquation one = new QuadraticEquation(s);

        one.solve();
    }
}
