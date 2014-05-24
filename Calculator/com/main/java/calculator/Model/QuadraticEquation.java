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

        printLists();

        prepareVariables();
    }

    private void prepareVariables() {
        // quadratic factor
        if(!getBody().getQ_tokens().isEmpty()) {
            for(int i = 0; i < getBody().getQ_tokens().get(0).length(); i++) {
                if(Character.isLetter(getBody().getQ_tokens().get(0).charAt(i))) {
                    if((i - 1) >= 0 && Character.isDigit(getBody().getQ_tokens().get(0).charAt(i - 1))) {
                        quadraticMem = Integer.parseInt(getBody().getQ_tokens().get(0).substring(0, i));
                    } else if((i - 1) >= 0 && getBody().getQ_tokens().get(0).charAt(i - 1) == '-') {
                        quadraticMem = -1;
                    } else if((i - 1) < 0) {
                        quadraticMem = 1;
                    }
                }
            }

            getBody().getQ_tokens().clear();
        }


        // unknown factor
        if(!getBody().getU_tokens().isEmpty()) {
            for(int i = 0; i < getBody().getU_tokens().get(0).length(); i++) {
                if(Character.isLetter(getBody().getU_tokens().get(0).charAt(i))) {
                    if((i - 1) >= 0 && Character.isDigit(getBody().getU_tokens().get(0).charAt(i - 1))) {
                        standardMem = Integer.parseInt(getBody().getU_tokens().get(0).substring(0, i));
                    } else if((i - 1) >= 0 && getBody().getU_tokens().get(0).charAt(i - 1) == '-') {
                        standardMem = -1;
                    } else if((i - 1) < 0) {
                        standardMem = 1;
                    }
                }
            }

            getBody().getU_tokens().clear();
        }

        // free factor
        if(!getBody().getK_tokens().isEmpty()) {
            freeMem = Integer.parseInt(getBody().getK_tokens().get(0));

            getBody().getK_tokens().clear();
        }
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
