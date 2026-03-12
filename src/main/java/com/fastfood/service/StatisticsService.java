package com.fastfood.service;

import com.fastfood.model.Order;
import com.fastfood.model.OrderItem;
import com.fastfood.repository.OrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Thống kê doanh thu và phân tích bán hàng.
 * Yêu cầu: sử dụng stream().collect().
 */
public class StatisticsService {
    private final OrderRepository orderRepository;

    public StatisticsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /** Tổng doanh thu từ các đơn hàng PAID. */
    public double getTotalRevenue() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PAID)
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    /** Doanh thu (VND) theo từng tên món. */
    public Map<String, Double> getRevenueByItem() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PAID)
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getMenuItem().getName(),
                        Collectors.summingDouble(OrderItem::getSubtotal)));
    }

    /** Số lượng bán (cái/ly) theo từng tên món. */
    public Map<String, Integer> getSalesByItem() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PAID)
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getMenuItem().getName(),
                        Collectors.summingInt(OrderItem::getQuantity)));
    }

    /** Tên món bán chạy nhất (theo số lượng). */
    public Optional<String> getBestSellingItem() {
        return getSalesByItem().entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }

    /** Tổng số đơn hàng đã thanh toán. */
    public long getTotalPaidOrderCount() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PAID)
                .count();
    }

    /** Danh sách top N món bán chạy nhất. */
    public List<Map.Entry<String, Integer>> getTopSellingItems(int topN) {
        return getSalesByItem().entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }
}
