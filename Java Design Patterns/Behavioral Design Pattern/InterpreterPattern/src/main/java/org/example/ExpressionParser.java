package org.example;

public class ExpressionParser {
    public Expression parseExpression(String expression) {
        // Simple parser for basic expressions like "5 + 3", "10 - 2", etc.
        String[] parts = expression.trim().split(" ");

        if (parts.length == 3) {
            int left = Integer.parseInt(parts[0]);
            String operator = parts[1];
            int right = Integer.parseInt(parts[2]);

            Expression leftExpr = new NumberExpression(left);
            Expression rightExpr = new NumberExpression(right);

            switch (operator) {
                case "+":
                    return new AdditionExpression(leftExpr, rightExpr);
                case "-":
                    return new SubtractionExpression(leftExpr, rightExpr);
                case "*":
                    return new MultiplicationExpression(leftExpr, rightExpr);
                case "/":
                    return new DivisionExpression(leftExpr, rightExpr);
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        } else if (parts.length == 1) {
            // Single number
            return new NumberExpression(Integer.parseInt(parts[0]));
        }

        throw new IllegalArgumentException("Invalid expression format");
    }
}
