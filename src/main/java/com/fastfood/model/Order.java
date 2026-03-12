package com.fastfood.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum OrderStatus {
        PENDING, PAID, CANCELLED
    }
    private static int counter = 1;

    private final String orderId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;

    public Order() {
        this.orderId = String.format("ORD%03d", counter++);
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void removeItem(String menuItemId) {
        items.removeIf(i -> i.getMenuItem().getId().equals(menuItemId));
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Đơn hàng : %s | Trạng thái: %s | Thời gian: %s%n",
                orderId, status, createdAt.format(fmt)));
        if (items.isEmpty()) {
            sb.append("  (Chưa có món)\n");
        } else {
            items.forEach(item -> sb.append(item).append("\n"));
            sb.append(String.format("  %42s %,10.0f VND%n", "TỔNG CỘNG:", getTotalPrice()));
        }
        return sb.toString();
    }

    public static void resetCounter() {
        counter = 1;
    }
}
