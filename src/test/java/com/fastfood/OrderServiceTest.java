package com.fastfood.service;

import com.fastfood.exception.InsufficientStockException;
import com.fastfood.exception.InvalidOrderIdException;
import com.fastfood.model.MenuItem;
import com.fastfood.model.Order;
import com.fastfood.model.OrderItem;
import com.fastfood.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private MenuService menuService;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = Mockito.mock(OrderRepository.class);
        menuService = Mockito.mock(MenuService.class);
        orderService = new OrderService(orderRepository, menuService);
    }
    @Test
    void testCreateOrder() {
        Order order = orderService.createOrder();
        verify(orderRepository, times(1)).save(order);
        assertNotNull(order);
    }
    @Test
    void testAddItemToOrderSuccess() throws Exception {
        Order order = new Order();
        String orderId = order.getId();
        MenuItem menuItem = mock(MenuItem.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(menuService.findById("F1")).thenReturn(Optional.of(menuItem));
        orderService.addItemToOrder(orderId, "F1", 2);
        verify(menuService).reduceStock("F1", 2);
        assertEquals(1, order.getItems().size());
    }
    @Test
    void testAddItemInvalidOrder() {
        when(orderRepository.findById("O99")).thenReturn(Optional.empty());
        assertThrows(InvalidOrderIdException.class, () -> {
            orderService.addItemToOrder("O99", "F1", 2);
        });
    }
    @Test
    void testRemoveItemFromOrder() throws Exception {
        Order order = new Order();
        String orderId = order.getId();
        MenuItem item = mock(MenuItem.class);
        when(item.getId()).thenReturn("F1");
        OrderItem orderItem = new OrderItem(item, 2);
        order.addItem(orderItem);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.removeItemFromOrder(orderId, "F1");
        verify(menuService).restoreStock("F1", 2);
        assertTrue(order.getItems().isEmpty());
    }
    @Test
    void testPayOrder() throws Exception {
        Order order = new Order();
        String orderId = order.getId();
        MenuItem item = mock(MenuItem.class);
        order.addItem(new OrderItem(item, 1));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.payOrder(orderId);
        assertEquals(Order.OrderStatus.PAID, order.getStatus());
    }
    @Test
    void testCancelOrder() throws Exception {
        Order order = new Order();
        String orderId = order.getId();
        MenuItem item = mock(MenuItem.class);
        when(item.getId()).thenReturn("F1");
        order.addItem(new OrderItem(item, 3));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.cancelOrder(orderId);
        verify(menuService).restoreStock("F1", 3);
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus());
    }
    @Test
    void testCalculateTotal() throws Exception {
        Order order = new Order();
        String orderId = order.getId();
        MenuItem item = mock(MenuItem.class);
        when(item.calculatePrice()).thenReturn(50000.0);
        order.addItem(new OrderItem(item, 2));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        double total = orderService.calculateTotal(orderId);
        assertEquals(100000, total);
    }
}