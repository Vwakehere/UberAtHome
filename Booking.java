public class Booking {
    private String bookingId;
    private String username;
    private String driverName;
    private String carModel;
    private String carType;
    private String source;
    private String destination;
    private int numberOfPassengers;
    private double totalFare;
    private String status; // "Pending", "Confirmed", "Completed"

    public Booking(String bookingId, String username, String driverName, String carModel, 
                   String carType, String source, String destination, int numberOfPassengers, 
                   double totalFare) {
        this.bookingId = bookingId;
        this.username = username;
        this.driverName = driverName;
        this.carModel = carModel;
        this.carType = carType;
        this.source = source;
        this.destination = destination;
        this.numberOfPassengers = numberOfPassengers;
        this.totalFare = totalFare;
        this.status = "Confirmed";
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUsername() {
        return username;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarType() {
        return carType;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

