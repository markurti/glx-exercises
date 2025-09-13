package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getItemCount() {
        return items.size();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "Cart is empty";
        }
        StringBuilder sb = new StringBuilder("Cart contents:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("  %d. %s%n", i + 1, items.get(i)));
        }
        return sb.toString();
    }
}
