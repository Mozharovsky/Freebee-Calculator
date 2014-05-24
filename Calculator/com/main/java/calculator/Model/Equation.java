package com.main.java.calculator.Model;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public abstract class Equation {
    private Type type = Type.NONE;
    private Body body;

    public Equation(String equationBody) {
        body = new Body(equationBody);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Body getBody() { return body; }

    public void printLists() {
        if(!body.getQ_tokens().isEmpty())
            System.out.printf("[Quadratic mems]: %s.%n", body.getQ_tokens());

        if(!body.getU_tokens().isEmpty())
            System.out.printf("[Unknown mems]: %s.%n", body.getU_tokens());

        if(!body.getK_tokens().isEmpty())
            System.out.printf("[Known mems]: %s.%n", body.getK_tokens());
    }
}
