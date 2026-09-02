package mapping;

public class River extends Line {

    private String name;

    public River(String name, double[][] locations) {
        super(locations);
        this.name = name;
    }

    public River(String name, String[] locations) {
        super(locations);
        this.name = name;
    }

    @Override
    protected String getName() {
        return name;
    }
}
