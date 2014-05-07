package com.main.java.calculator;

import com.main.java.calculator.Model.QuadraticEquation;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class Main {
    public static void main(String[] args) {
        String s = "25 - 25 + 9981 - 88k";
        QuadraticEquation one = new QuadraticEquation(s);

        one.printLists();
    }
}
