package mapping;

import java.util.Arrays;

/**
 * A mappable path made of multiple (latitude, longitude) points,
 * e.g. the course of a river.
 */
public abstract class Line implements Mappable {

    private double[][] locations;

    public Line(double[][] locations) {
        this.locations = locations;
    }

    public Line(String[] locations) {
        this.locations = new double[locations.length][];
        for (int i = 0; i < locations.length; i++) {
            this.locations[i] = Mappable.stringToLatLon(locations[i]);
        }
    }

    private String locations() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < locations.length; i++) {
            sb.append(Arrays.toString(locations[i]));
            if (i < locations.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    protected abstract String getName();

    @Override
    public void render() {
        System.out.println("Render " + getName() + " as LINE (" + locations() + ")");
    }
}
