package org.example;

@FunctionalInterface
public interface CategorizeMenuItem {
    String categorize(MenuItem menuItem);

    // Default method for handling unknown categories
    default String categorizeWithDefault(MenuItem menuItem, String defaultCategory) {
        try {
            String category = categorize(menuItem);
            return (category == null || category.trim().isEmpty()) ? defaultCategory : category;
        } catch (Exception e) {
            return defaultCategory;
        }
    }
}
