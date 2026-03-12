
package com.fastfood.service;

import com.fastfood.exception.InsufficientStockException;
import com.fastfood.exception.InvalidOrderIdException;
import com.fastfood.model.MenuItem;
import com.fastfood.model.Order;
import com.fastfood.model.OrderItem;
import com.fastfood.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

/**
 * Logic nghiệp vụ cho đơn hàng.
 * Dùng Optional để xử lý khi không tìm thấy đơn hàng.
 */
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuService menuService;

    public OrderService(OrderRepository orderRepository, MenuService menuService) {
        this.orderRepository = orderRepository;
        this.menuService = menuService;
    }

    /** Tạo đơn hàng mới (trạng thái PENDING). */
    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }

    /**
     * Thêm món vào đơn hàng đang mở.
     * Kiểm tra đơn hàng tồn tại, món tồn tại, và đủ tồn kho.
     */
    public void addItemToOrder(String orderId, String menuItemId, int quantity)
            throws InvalidOrderIdException, InsufficientStockException {
        Order order = getOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Không thể thêm món: đơn hàng đã " + order.getStatus());
        }
        MenuItem menuItem = menuService.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy món với ID: " + menuItemId));
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
        menuService.reduceStock(menuItemId, quantity);
        order.addItem(new OrderItem(menuItem, quantity));
    }

    /** Xóa một dòng món khỏi đơn hàng PENDING và hoàn lại tồn kho. */
    public void removeItemFromOrder(String orderId, String menuItemId)
            throws InvalidOrderIdException {
        Order order = getOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Chỉ được xóa món ở đơn hàng PENDING.");
        }
        order.getItems().stream()
                .filter(i -> i.getMenuItem().getId().equals(menuItemId))
                .findFirst()
                .ifPresent(item -> {
                    menuService.restoreStock(menuItemId, item.getQuantity());
                    order.removeItem(menuItemId);
                });
    }

    /** Thanh toán đơn hàng. */
    public void payOrder(String orderId) throws InvalidOrderIdException {
        Order order = getOrderById(orderId);
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Chỉ có thể thanh toán đơn hàng PENDING.");
        }
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("Đơn hàng chưa có món nào.");
        }
        order.setStatus(Order.OrderStatus.PAID);
    }

    /** Hủy đơn hàng và hoàn lại tồn kho. */
    public void cancelOrder(String orderId) throws InvalidOrderIdException {
        Order order = getOrderById(orderId);
        if (order.getStatus() == Order.OrderStatus.PAID) {
            throw new IllegalStateException("Không thể hủy đơn hàng đã thanh toán.");
        }
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new IllegalStateException("Đơn hàng đã được hủy trước đó.");
        }
        // Hoàn lại tồn kho cho từng món
        order.getItems().forEach(item -> menuService.restoreStock(
                item.getMenuItem().getId(), item.getQuantity()));
        order.setStatus(Order.OrderStatus.CANCELLED);
    }

    /** Lấy đơn hàng theo ID; ném InvalidOrderIdException nếu không tìm thấy. */
    public Order getOrderById(String orderId) throws InvalidOrderIdException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidOrderIdException(
                        "Không tìm thấy đơn hàng với ID: " + orderId));
    }

    /** Trả về Optional – dùng khi không muốn bắt exception. */
    public Optional<Order> findOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public double calculateTotal(String orderId) throws InvalidOrderIdException {
        return getOrderById(orderId).getTotalPrice();
    }
}
