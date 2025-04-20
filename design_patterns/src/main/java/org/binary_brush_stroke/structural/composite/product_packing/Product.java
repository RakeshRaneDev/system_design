package org.binary_brush_stroke.structural.composite.product_packing;

public abstract class Product implements Box{
    protected final String title;
    protected final double price;

    protected Product(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }
}
