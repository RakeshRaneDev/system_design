package org.binary_brush_stroke.structural.adaptor.restanrant;

public class MultiRestAppMain {
    public static void main(String[] args){
        MultiRestoApp app = new MultiRestoAppImpl();
        app.displayMenu(new XmlData());

        MultiRestoApp newApp = new FancyUiAdapter();
        newApp.displayMenu(new XmlData());

    }
}
