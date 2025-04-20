package org.binary_brush_stroke.structural.composite.product_packing;

public class Book extends Product{

    Book(String title, double price){
        super(title, price);

    }
    @Override
    public double calculatePrice() {
        return getPrice();
    }


}
