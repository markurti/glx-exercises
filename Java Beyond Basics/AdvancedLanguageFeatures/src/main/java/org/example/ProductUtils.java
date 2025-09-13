package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductUtils {
    public static double getTotalValue(List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.price() * product.quantity())
                .sum();
    }

    public static Optional<Product> getMostExpensiveProduct(List<Product> products) {
        return products.stream()
                .max(Comparator.comparing(Product::price));
    }
}
