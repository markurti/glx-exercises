package org.example;

public class DivisionExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;

    public DivisionExpression(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }

    @Override
    public int interpret() {
        int rightValue = rightExpression.interpret();
        if (rightValue == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return leftExpression.interpret() / rightValue;
    }
}
