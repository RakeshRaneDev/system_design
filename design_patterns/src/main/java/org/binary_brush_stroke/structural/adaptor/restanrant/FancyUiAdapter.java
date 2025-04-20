package org.binary_brush_stroke.structural.adaptor.restanrant;

public class FancyUiAdapter implements MultiRestoApp{
    FancyUiService fancyUiService;
    FancyUiAdapter(){
        this.fancyUiService = new FancyUiService();
    }

    @Override
    public void displayMenu(XmlData data) {
        fancyUiService.displayMenu(convertToJson( data));

    }

    @Override
    public void displayRecommendations(XmlData data) {
        fancyUiService.displayRecommendations(convertToJson( data));

    }
    private JsonData convertToJson(XmlData data){
        System.out.println("Converting to json");
        return new JsonData();
    }
}
