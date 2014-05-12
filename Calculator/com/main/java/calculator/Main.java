package com.main.java.calculator;

import java.util.ArrayList;
import com.main.java.calculator.Model.QuadraticEquation;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class Main {
    public static void main(String[] args) {
        String s = "56x^2 + 6x + 5 = 8x + 54x^2 + 4";
        QuadraticEquation one = new QuadraticEquation(s);

        one.printLists();
    }
}
