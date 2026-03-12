package com.fastfood.repository;

import com.fastfood.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuRepository {

    private List<MenuItem> menuItems = new ArrayList<>();

    // Thêm món vào menu
    public void add(MenuItem item) {
        menuItems.add(item);
    }

    // Lấy toàn bộ menu
    public List<MenuItem> findAll() {
        return menuItems;
    }

    // Tìm món theo ID
    public Optional<MenuItem> findById(int id) {
        return menuItems.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    // Xóa món theo ID
    public boolean deleteById(int id) {
        return menuItems.removeIf(item -> item.getId() == id);
    }

    // Cập nhật món
    public void update(MenuItem updatedItem) {
        findById(updatedItem.getId()).ifPresent(item -> {
            item.setName(updatedItem.getName());
            item.setPrice(updatedItem.getPrice());
            item.setStock(updatedItem.getStock());
        });
    }
}