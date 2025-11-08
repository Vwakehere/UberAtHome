public class Location {
    private String name;
    private double distance; // in km
    private double baseFare; // base fare in rupees

    public Location(String name, double distance, double baseFare) {
        this.name = name;
        this.distance = distance;
        this.baseFare = baseFare;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public double getBaseFare() {
        return baseFare;
    }
}

