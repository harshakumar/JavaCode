package mapping;

public class Main {

    public static void main(String[] args) {

        // -- Parks layer (Layer<Park>) --
        Layer<Park> parksLayer = new Layer<>();
        parksLayer.addElement(new Park("Yellowstone National Park", "44.4882, -110.5916"));
        parksLayer.addElement(new Park("Grand Canyon National Park", "36.0636, -112.1079"));
        parksLayer.addElement(new Park("Yosemite National Park", "37.8855, -119.5360"));

        // -- Rivers layer (Layer<River>) --
        Layer<River> riversLayer = new Layer<>();
        riversLayer.addElement(new River("Mississippi River",
                new String[]{"47.2160, -95.2348", "35.1556, -90.0659", "29.1566, -89.2495"}));
        riversLayer.addElement(new River("Missouri River",
                new String[]{"45.9239, -111.4983", "38.8146, -90.1218"}));
        riversLayer.addElement(new River("Colorado River",
                new String[]{"40.4708, -105.8286", "36.1015, -112.0892", "34.2964, -114.1148", "31.7811, -114.7724"}));
        riversLayer.addElement(new River("Delaware River",
                new String[]{"42.2026, -75.00836", "39.4955, -75.5592"}));

        System.out.println("== National Parks ==");
        parksLayer.renderLayer();

        System.out.println();
        System.out.println("== Rivers ==");
        riversLayer.renderLayer();
    }
}
