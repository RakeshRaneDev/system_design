package org.binary_brush_stroke.structural.decorator.shape;

public class ShapeMain {
    public static void main(String[] args){
        Shape square = new Square();
        Shape circle = new Circle();
        ShapeDecorator redSquare = new RedShape(new Square());
        ShapeDecorator redCircle = new RedShape( new Circle());
        circle.draw();
        redSquare.draw();

    }
}
