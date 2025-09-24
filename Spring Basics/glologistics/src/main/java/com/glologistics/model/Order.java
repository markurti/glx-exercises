// Order Model
package com.glologistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int orderId;
    private double orderTotalAmount;
    private LocalDate orderDate;
    private OrderStatus orderStatus;
    private int productId;
    private int custId;
    private long orderProductQuantity;
}