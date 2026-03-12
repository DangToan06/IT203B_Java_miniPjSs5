package com.fastfood.repository;

import com.fastfood.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    private List<Order> orders = new ArrayList<>();

    // Lưu đơn hàng
    public void save(Order order) {
        orders.add(order);
    }

    // Lấy tất cả đơn
    public List<Order> findAll() {
        return orders;
    }

    // Tìm đơn theo ID
    public Optional<Order> findById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    // Xóa đơn
    public boolean deleteById(int id) {
        return orders.removeIf(order -> order.getId() == id);
    }
}