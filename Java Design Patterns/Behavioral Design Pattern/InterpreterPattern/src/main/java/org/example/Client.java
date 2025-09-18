package org.example;

public class Client {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();

        System.out.println("=== Arithmetic Expression Interpreter ===");

        // Test different arithmetic expressions
        String[] expressions = {
                "10 + 5",
                "20 - 8",
                "6 * 4",
                "15 / 3",
                "42"
        };

        for (String expr : expressions) {
            try {
                Expression expression = parser.parseExpression(expr);
                int result = expression.interpret();
                System.out.println(expr + " = " + result);
            } catch (Exception e) {
                System.out.println("Error evaluating '" + expr + "': " + e.getMessage());
            }
        }

        // Test division by zero
        System.out.println("\nTesting edge case:");
        try {
            Expression divByZero = parser.parseExpression("10 / 0");
            divByZero.interpret();
        } catch (ArithmeticException e) {
            System.out.println("10 / 0 - Error: " + e.getMessage());
        }
    }
}