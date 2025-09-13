package org.example;

@FunctionalInterface
public interface ApplySpecialOffer {
    MenuItem applyOffer(MenuItem menuItem);

    // Default method to chain multiple offers
    default ApplySpecialOffer andThen(ApplySpecialOffer after) {
        return (MenuItem item) -> after.applyOffer(this.applyOffer(item));
    }
}
