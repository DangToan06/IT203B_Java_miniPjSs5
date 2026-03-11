package com.fastfood.model;

/**
 * Đồ ăn – kế thừa MenuItem.
 * calculatePrice() trả về đúng giá gốc.
 */
public class Food extends MenuItem {
    private String category;

    public Food(String id, String name, double price, int stock, String category) {
        super(id, name, price, stock);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public double calculatePrice() {
        return getPrice();
    }

    @Override
    public String toString() {
        return super.toString() + " [Đồ ăn – " + category + "]";
    }
}
