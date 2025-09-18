package org.example;

public class MultiplicationExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;

    public MultiplicationExpression(Expression left, Expression right) {
        this.leftExpression = left;
        this.rightExpression = right;
    }

    @Override
    public int interpret() {
        return leftExpression.interpret() * rightExpression.interpret();
    }
}
