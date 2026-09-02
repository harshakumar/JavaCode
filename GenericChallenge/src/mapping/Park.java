package mapping;

public class Park extends Point {

    private String name;

    public Park(String name, double[] location) {
        super(location);
        this.name = name;
    }

    public Park(String name, String location) {
        super(location);
        this.name = name;
    }

    @Override
    protected String getName() {
        return name;
    }
}
