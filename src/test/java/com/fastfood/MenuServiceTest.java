package com.fastfood.service;

import com.fastfood.exception.InsufficientStockException;
import com.fastfood.model.MenuItem;
import com.fastfood.repository.MenuRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {
    private MenuService menuService;
    private FakeMenuRepository repository;
    @BeforeEach
    void setup() {
        repository = new FakeMenuRepository();
        menuService = new MenuService(repository);
    }
    @Test
    void testAddMenuItem() {
        MenuItem item = new TestMenuItem("F1", "Burger", 50000, 10);
        menuService.addMenuItem(item);
        assertEquals(1, repository.findAll().size());
    }
    @Test
    void testRemoveMenuItem() {
        MenuItem item = new TestMenuItem("F1", "Burger", 50000, 10);
        repository.add(item);
        menuService.removeMenuItem("F1");
        assertTrue(repository.findAll().isEmpty());
    }
    @Test
    void testFilterByName() {
        repository.add(new TestMenuItem("F1", "Burger", 50000, 10));
        repository.add(new TestMenuItem("F2", "Pizza", 60000, 10));
        List<MenuItem> result = menuService.filterByName("bur");
        assertEquals(1, result.size());
    }
    @Test
    void testFilterByPriceRange() {
        repository.add(new TestMenuItem("F1", "Burger", 50000, 10));
        repository.add(new TestMenuItem("F2", "Pizza", 70000, 10));
        List<MenuItem> result = menuService.filterByPriceRange(40000, 60000);
        assertEquals(1, result.size());
    }
    @Test
    void testReduceStockSuccess() throws Exception {
        MenuItem item = new TestMenuItem("F1", "Burger", 50000, 10);
        repository.add(item);
        menuService.reduceStock("F1", 3);
        assertEquals(7, item.getStock());
    }
    @Test
    void testReduceStockFail() {
        MenuItem item = new TestMenuItem("F1", "Burger", 50000, 2);
        repository.add(item);
        assertThrows(InsufficientStockException.class, () -> {
            menuService.reduceStock("F1", 5);
        });
    }
    @Test
    void testRestoreStock() {
        MenuItem item = new TestMenuItem("F1", "Burger", 50000, 5);
        repository.add(item);
        menuService.restoreStock("F1", 3);
        assertEquals(8, item.getStock());
    }
}
