package com.main.java.calculator.Math;

import com.main.java.calculator.Model.Equation;
import com.main.java.calculator.Model.QuadraticEquation;
import com.main.java.calculator.Model.Type;
import java.util.ArrayList;

/**
 * Created by E. Mozharovsky on 15.05.14.
 */
public class EquationMath {
    private EquationMath() {
        // no one can create instances of this class
    }

    public static ArrayList<String> findQuadraticDiscriminant(Equation equation) {
        if(equation.getType().equals(Type.QUADRATIC)) {
            ArrayList<String> rads = new ArrayList<String>();
            double sqrtDis = 0.0;

            QuadraticEquation _equation = (QuadraticEquation) equation;
            sqrtDis = Math.sqrt(_equation.getStandardMem() * _equation.getStandardMem() - 4 * _equation.getFreeMem() * _equation.getQuadraticMem());

            System.out.println("Quadratic " + _equation.getQuadraticMem());
            System.out.println("Standard " + _equation.getStandardMem());
            System.out.println("Free " + _equation.getFreeMem());

            if(sqrtDis > 0) {
                rads.add(Double.toString(((-1) * _equation.getStandardMem() + sqrtDis) / (2 * _equation.getQuadraticMem())));
                rads.add(Double.toString(((-1) * _equation.getStandardMem() - sqrtDis) / (2 * _equation.getQuadraticMem())));
            } else if(sqrtDis == 0) {
                rads.add(Double.toString(((-1) * _equation.getStandardMem() / (2 * _equation.getQuadraticMem()))));
            } else if(sqrtDis < 0) {
                rads = null;
                System.out.println("No radicals!");
            }

            return rads;
        } else {
            return null;
        }
    }
}
