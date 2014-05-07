package com.main.java.calculator.Model;

/**
 * Created by serjrebko on 07.05.14.
 */
public class QuadraticEquation extends Equation {
    private int quadraticMem = 0;
    private int standardMem = 0;
    private int freeMem = 0;

    public QuadraticEquation(String equationBody) {
        super(equationBody);
        setType(Type.QUADRATIC);
    }
}
