package mapping;

/**
 * Anything that can be rendered on a map implements this interface.
 * Includes a static helper for parsing a "lat, lon" string into a
 * double array, shared by both Point and Line.
 */
public interface Mappable {

    void render();

    static double[] stringToLatLon(String location) {
        String[] parts = location.split(",");
        double[] latLon = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            latLon[i] = Double.parseDouble(parts[i].trim());
        }
        return latLon;
    }
}
