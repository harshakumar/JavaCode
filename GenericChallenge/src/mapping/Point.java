package mapping;

import java.util.Arrays;

/**
 * A single mappable location (latitude, longitude).
 * Abstract because Point is only ever meaningful through a
 * specific subclass, e.g. Park.
 */
public abstract class Point implements Mappable {

    private double[] location;

    public Point(double[] location) {
        this.location = location;
    }

    public Point(String location) {
        this.location = Mappable.stringToLatLon(location);
    }

    private String location() {
        return Arrays.toString(location);
    }

    /**
     * Subclasses supply the display name (e.g. "Yellowstone National Park")
     * so render() can produce a complete, human-readable line.
     */
    protected abstract String getName();

    @Override
    public void render() {
        System.out.println("Render " + getName() + " as POINT (" + location() + ")");
    }
}
