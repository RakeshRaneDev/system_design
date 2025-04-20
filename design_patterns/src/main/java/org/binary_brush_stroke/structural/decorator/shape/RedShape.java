package org.binary_brush_stroke.structural.decorator.shape;

public class RedShape extends ShapeDecorator{
    RedShape(Shape shape) {
        super(shape);
    }
    @Override
    public void draw(){
        shape.draw();
        addRedColor();
    }
    private void addRedColor(){
        System.out.println("RedColor");
    }


}
