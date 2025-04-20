package org.binary_brush_stroke.structural.composite.product_packing;

public class OrderMain {
    public static void main(String[] args){
        DeliveryService service  = new DeliveryService();
        service.setUpOrder(new CompositeBox(new Book("two state", 100),
                        new CompositeBox(new Book("two state", 100), new Book("two state", 500)))


        );
        System.out.println(service.calculateOrderPrice());
    }
}
