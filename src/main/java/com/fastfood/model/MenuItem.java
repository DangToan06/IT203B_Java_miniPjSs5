package com.fastfood.model;

/**
 * Abstract class đại diện cho một món trong thực đơn.
 * Food và Drink kế thừa class này.
 */
public abstract class MenuItem {
    private String id;
    private String name;
    private double price;
    private int stock;

    public MenuItem(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ===== Getters & Setters =====
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Tính giá thực tế của món (có thể khác giá gốc, ví dụ: Drink theo size).
     */
    public abstract double calculatePrice();

    @Override
    public String toString() {
        return String.format("[%s] %-30s | Giá: %,7.0f VND | Còn: %d",
                id, name, calculatePrice(), stock);
    }
}
