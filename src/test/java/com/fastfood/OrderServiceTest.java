package com.fastfood.service;

import com.fastfood.model.MenuItem;
import com.fastfood.model.Order;
import com.fastfood.model.OrderItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void testCalculateTotal() {
        MenuItem item = new MenuItem("F1", "Burger", 50000, 10) {
            @Override
            public double calculatePrice() {
                return getPrice();
            }
        };
        OrderItem orderItem = new OrderItem(item, 2);
        Order order = new Order();
        order.addItem(orderItem);
        double total = order.calculateTotal();
        assertEquals(100000, total);
    }
}
