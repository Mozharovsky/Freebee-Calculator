package com.main.java.calculator.Model;

import com.main.java.calculator.Math.EquationMath;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class QuadraticEquation extends Equation {
    private int quadraticMem = 0;
    private int standardMem = 0;
    private int freeMem = 0;

    public QuadraticEquation(String equationBody) {
        super(equationBody);
        setType(Type.QUADRATIC);
        prepareVariables();
    }

    private void prepareVariables() {
        // quadratic factor
        getBody().getQuadraticMems().set(0, getBody().getQuadraticMems().get(0).replace("^2", ""));
        for(int i = 0; i < getBody().getQuadraticMems().get(0).length(); i++) {
            if(Character.isLetter(getBody().getQuadraticMems().get(0).charAt(i))) {
                getBody().getQuadraticMems().set(0, getBody().getQuadraticMems().get(0).replace(Character.toString(getBody().getQuadraticMems().get(0).charAt(i)), ""));
            }
        }

        quadraticMem = Integer.parseInt(getBody().getQuadraticMems().get(0));
        getBody().getQuadraticMems().clear();

        // unknown factor
        for(int i = 0; i < getBody().getUnknownMems().get(0).length(); i++) {
            if(Character.isLetter(getBody().getUnknownMems().get(0).charAt(i))) {
                getBody().getUnknownMems().set(0, getBody().getUnknownMems().get(0).replace(Character.toString(getBody().getUnknownMems().get(0).charAt(i)), ""));
            }
        }

        standardMem = Integer.parseInt(getBody().getUnknownMems().get(0));
        getBody().getUnknownMems().clear();

        // free factor
        freeMem = getBody().getKnownMems().get(0);
        getBody().getKnownMems().clear();
    }

    public void solve() {
        System.out.println(EquationMath.findQuadraticDiscriminant(this));
    }

    // getters
    public int getQuadraticMem() {
        return quadraticMem;
    }

    public int getStandardMem() {
        return standardMem;
    }

    public int getFreeMem() {
        return freeMem;
    }
}
