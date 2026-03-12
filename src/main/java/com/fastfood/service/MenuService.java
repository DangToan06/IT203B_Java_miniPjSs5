package com.fastfood.service;

import com.fastfood.exception.InsufficientStockException;
import com.fastfood.model.MenuItem;
import com.fastfood.repository.MenuRepository;
import com.fastfood.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuService {
    private final MenuRepository repository;

    public MenuService(MenuRepository repository) {
        this.repository = repository;
    }

    public void addMenuItem(MenuItem item) {
        Validator.validateMenuItem(item);
        repository.add(item);
    }

    public void removeMenuItem(String id) {
        repository.remove(id);
    }

    public void updateMenuItem(MenuItem item) {
        Validator.validateMenuItem(item);
        repository.update(item);
    }

    public List<MenuItem> getAllMenuItems() {
        return repository.findAll();
    }

    public Optional<MenuItem> findById(String id) {
        return repository.findById(id);
    }


    public List<MenuItem> filterByName(String keyword) {
        return repository.findAll().stream()
                .filter(item -> item.getName().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<MenuItem> filterByPriceRange(double minPrice, double maxPrice) {
        return repository.findAll().stream()
                .filter(item -> item.calculatePrice() >= minPrice
                        && item.calculatePrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<MenuItem> filterByMaxPrice(double maxPrice) {
        return repository.findAll().stream()
                .filter(item -> item.calculatePrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void reduceStock(String id, int quantity) throws InsufficientStockException {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy món với ID: " + id));
        if (item.getStock() < quantity) {
            throw new InsufficientStockException(
                    "Không đủ hàng tồn kho cho món: " + item.getName()
                            + " (còn: " + item.getStock() + ", yêu cầu: " + quantity + ")");
        }
        item.setStock(item.getStock() - quantity);
        repository.update(item);
    }

    public void restoreStock(String id, int quantity) {
        repository.findById(id).ifPresent(item -> {
            item.setStock(item.getStock() + quantity);
            repository.update(item);
        });
    }
}
