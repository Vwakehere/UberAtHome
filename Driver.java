public class Driver {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String carModel;
    private String carType; // "3-seater" or "5-seater"
    private String licenseNumber;
    private boolean available;
    private String currentLocation; // Current location of the driver

    public Driver(String username, String password, String name, String phone, 
                  String carModel, String carType, String licenseNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.carModel = carModel;
        this.carType = carType;
        this.licenseNumber = licenseNumber;
        this.available = true;
        this.currentLocation = null;
    }

    public Driver(String username, String password, String name, String phone, 
                  String carModel, String carType, String licenseNumber, String currentLocation) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.carModel = carModel;
        this.carType = carType;
        this.licenseNumber = licenseNumber;
        this.available = true;
        this.currentLocation = currentLocation;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarType() {
        return carType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}

