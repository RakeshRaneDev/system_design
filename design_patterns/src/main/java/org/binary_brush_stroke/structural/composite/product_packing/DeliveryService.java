package org.binary_brush_stroke.structural.composite.product_packing;

public class DeliveryService {
    Box box;
    DeliveryService(){

    }
    public void setUpOrder(Box... boxes){
        this.box = new CompositeBox(boxes);
    }
    public double calculateOrderPrice(){
        return box.calculatePrice();
    }
}
