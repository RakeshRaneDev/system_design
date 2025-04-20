package org.binary_brush_stroke.structural.decorator.shape;

public class ShapeDecorator implements Shape{
    Shape shape;
    ShapeDecorator(Shape shape){
        this.shape = shape;
    }

    @Override
    public void draw() {
        this.shape.draw();
    }
}
