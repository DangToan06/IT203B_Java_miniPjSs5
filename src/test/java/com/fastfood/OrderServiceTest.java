package com.fastfood.service;

import com.fastfood.model.MenuItem;
import com.fastfood.model.Order;
import com.fastfood.model.OrderItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    @Test
    void testCalculateTotal() {
        // tạo menu item
        MenuItem item = new MenuItem("F1", "Burger", 50000, 10) {
            @Override
            public double calculatePrice() {
                return getPrice();
            }
        };
        // tạo order item
        OrderItem orderItem = new OrderItem(item, 2);
        // tạo order
        Order order = new Order();
        // thêm item vào order
        order.addItem(orderItem);
        // tính tổng
        double total = order.calculateTotal();
        // kiểm tra kết quả
        assertEquals(100000, total);
    }
}
