package org.binary_brush_stroke.structural.composite.product_packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeBox implements Box{
    private final List<Box> childern  = new ArrayList<>();
    CompositeBox(Box... boxes){
        childern.addAll(Arrays.asList(boxes));

    }
    @Override
    public double calculatePrice() {
        return childern.stream().mapToDouble(Box::calculatePrice).sum();
    }
}
