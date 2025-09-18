package org.example;

public class SubtractionExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;

    public SubtractionExpression(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }

    @Override
    public int interpret() {
        return leftExpression.interpret() - rightExpression.interpret();
    }
}
