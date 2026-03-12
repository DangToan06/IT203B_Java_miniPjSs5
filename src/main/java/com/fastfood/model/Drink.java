package com.fastfood.model;

/**
 * Đồ uống – kế thừa MenuItem.
 * calculatePrice() điều chỉnh theo size (S/M/L).
 */
public class Drink extends MenuItem {

    public enum DrinkSize {
        S, M, L
    }

    private DrinkSize size;

    public Drink(String id, String name, double price, int stock, DrinkSize size) {
        super(id, name, price, stock);
        this.size = size;
    }

    public DrinkSize getSize() {
        return size;
    }

    public void setSize(DrinkSize size) {
        this.size = size;
    }

    @Override
    public double calculatePrice() {
        switch (size) {
            case S:
                return getPrice();
            case M:
                return getPrice() * 1.2;
            case L:
                return getPrice() * 1.5;
            default:
                return getPrice();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [Nước uống – Size " + size + "]";
    }
}