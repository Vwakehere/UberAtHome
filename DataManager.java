import java.util.*;

public class DataManager {
    private static DataManager instance;
    private Map<String, User> users;
    private Map<String, Driver> drivers;
    private List<Location> locations;
    private List<Booking> bookings;
    private Map<String, Map<String, Double>> distanceMatrix; // Distance between locations
    private int bookingCounter;

    private DataManager() {
        users = new HashMap<>();
        drivers = new HashMap<>();
        bookings = new ArrayList<>();
        distanceMatrix = new HashMap<>();
        bookingCounter = 1;
        initializeLocations();
        initializeDistanceMatrix();
        initializeDummyDrivers();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void initializeLocations() {
        locations = new ArrayList<>();
        // 10 locations in Srinagar
        locations.add(new Location("NIT Srinagar", 0, 0)); // Base location
        locations.add(new Location("Lal Chowk", 12.5, 150));
        locations.add(new Location("Pari Mahal", 8.0, 120));
        locations.add(new Location("Tulip Garden", 15.0, 180));
        locations.add(new Location("Hari Parbat", 10.0, 140));
        locations.add(new Location("Shankaracharya Temple", 9.5, 130));
        locations.add(new Location("Dal Lake", 11.0, 135));
        locations.add(new Location("Mughal Gardens", 13.5, 160));
        locations.add(new Location("Hazratbal Shrine", 14.0, 170));
        locations.add(new Location("Jamia Masjid", 11.5, 145));
    }

    private void initializeDistanceMatrix() {
        // Distance matrix between all locations (in km)
        // Format: distanceMatrix.get(source).get(destination) = distance
        
        String[] locNames = {"NIT Srinagar", "Lal Chowk", "Pari Mahal", "Tulip Garden", 
                             "Hari Parbat", "Shankaracharya Temple", "Dal Lake", 
                             "Mughal Gardens", "Hazratbal Shrine", "Jamia Masjid"};
        
        // Initialize all distances
        for (String source : locNames) {
            distanceMatrix.put(source, new HashMap<>());
            for (String dest : locNames) {
                if (source.equals(dest)) {
                    distanceMatrix.get(source).put(dest, 0.0);
                } else {
                    // Calculate approximate distances
                    double dist = calculateDistance(source, dest);
                    distanceMatrix.get(source).put(dest, dist);
                }
            }
        }
    }

    private double calculateDistance(String source, String dest) {
        // Approximate distances between locations in Srinagar
        Map<String, Double> distances = new HashMap<>();
        
        // Distances from NIT Srinagar
        distances.put("NIT Srinagar-Lal Chowk", 12.5);
        distances.put("NIT Srinagar-Pari Mahal", 8.0);
        distances.put("NIT Srinagar-Tulip Garden", 15.0);
        distances.put("NIT Srinagar-Hari Parbat", 10.0);
        distances.put("NIT Srinagar-Shankaracharya Temple", 9.5);
        distances.put("NIT Srinagar-Dal Lake", 11.0);
        distances.put("NIT Srinagar-Mughal Gardens", 13.5);
        distances.put("NIT Srinagar-Hazratbal Shrine", 14.0);
        distances.put("NIT Srinagar-Jamia Masjid", 11.5);
        
        // Inter-location distances (approximate)
        distances.put("Lal Chowk-Pari Mahal", 6.5);
        distances.put("Lal Chowk-Tulip Garden", 8.0);
        distances.put("Lal Chowk-Hari Parbat", 3.5);
        distances.put("Lal Chowk-Shankaracharya Temple", 4.0);
        distances.put("Lal Chowk-Dal Lake", 2.5);
        distances.put("Lal Chowk-Mughal Gardens", 5.0);
        distances.put("Lal Chowk-Hazratbal Shrine", 6.0);
        distances.put("Lal Chowk-Jamia Masjid", 1.5);
        
        distances.put("Pari Mahal-Tulip Garden", 7.5);
        distances.put("Pari Mahal-Hari Parbat", 4.0);
        distances.put("Pari Mahal-Shankaracharya Temple", 3.5);
        distances.put("Pari Mahal-Dal Lake", 5.0);
        distances.put("Pari Mahal-Mughal Gardens", 6.5);
        distances.put("Pari Mahal-Hazratbal Shrine", 7.0);
        distances.put("Pari Mahal-Jamia Masjid", 5.5);
        
        distances.put("Tulip Garden-Hari Parbat", 6.5);
        distances.put("Tulip Garden-Shankaracharya Temple", 7.0);
        distances.put("Tulip Garden-Dal Lake", 5.5);
        distances.put("Tulip Garden-Mughal Gardens", 3.0);
        distances.put("Tulip Garden-Hazratbal Shrine", 4.0);
        distances.put("Tulip Garden-Jamia Masjid", 6.0);
        
        distances.put("Hari Parbat-Shankaracharya Temple", 2.5);
        distances.put("Hari Parbat-Dal Lake", 2.0);
        distances.put("Hari Parbat-Mughal Gardens", 4.5);
        distances.put("Hari Parbat-Hazratbal Shrine", 5.5);
        distances.put("Hari Parbat-Jamia Masjid", 1.0);
        
        distances.put("Shankaracharya Temple-Dal Lake", 3.5);
        distances.put("Shankaracharya Temple-Mughal Gardens", 5.0);
        distances.put("Shankaracharya Temple-Hazratbal Shrine", 6.0);
        distances.put("Shankaracharya Temple-Jamia Masjid", 2.5);
        
        distances.put("Dal Lake-Mughal Gardens", 4.0);
        distances.put("Dal Lake-Hazratbal Shrine", 5.0);
        distances.put("Dal Lake-Jamia Masjid", 1.5);
        
        distances.put("Mughal Gardens-Hazratbal Shrine", 2.5);
        distances.put("Mughal Gardens-Jamia Masjid", 4.5);
        
        distances.put("Hazratbal Shrine-Jamia Masjid", 3.5);
        
        // Check both directions
        String key1 = source + "-" + dest;
        String key2 = dest + "-" + source;
        
        if (distances.containsKey(key1)) {
            return distances.get(key1);
        } else if (distances.containsKey(key2)) {
            return distances.get(key2);
        }
        
        // Default: calculate based on base distances if not found
        Location srcLoc = getLocationByName(source);
        Location destLoc = getLocationByName(dest);
        if (srcLoc != null && destLoc != null) {
            return Math.abs(srcLoc.getDistance() - destLoc.getDistance()) + 2.0;
        }
        
        return 5.0; // Default distance
    }

    private void initializeDummyDrivers() {
        // Add 15 dummy drivers
        String[] driverNames = {"Ahmad Khan", "Bilal Sheikh", "Danish Mir", "Faisal Wani", 
                               "Ghulam Nabi", "Hassan Bhat", "Iqbal Lone", "Javed Dar",
                               "Kashif Shah", "Latif Malik", "Mohammad Yousuf", "Naseer Ahmad",
                               "Omar Farooq", "Parvez Ali", "Qasim Hassan"};
        
        String[] carModels = {"Maruti Swift", "Hyundai i20", "Tata Indica", "Mahindra XUV",
                             "Toyota Innova", "Honda City", "Maruti Dzire", "Hyundai Creta",
                             "Tata Nexon", "Mahindra Bolero", "Toyota Etios", "Honda Amaze",
                             "Maruti Ertiga", "Hyundai Venue", "Tata Tiago"};
        
        String[] carTypes = {"3-seater", "5-seater", "3-seater", "5-seater", "5-seater",
                            "3-seater", "5-seater", "5-seater", "3-seater", "5-seater",
                            "3-seater", "5-seater", "5-seater", "3-seater", "5-seater"};
        
        // Get all location names for random assignment
        String[] locationNames = new String[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            locationNames[i] = locations.get(i).getName();
        }
        
        java.util.Random random = new java.util.Random();
        
        for (int i = 0; i < 15; i++) {
            String username = "driver" + (i + 1);
            String password = "pass" + (i + 1);
            String license = "JK" + String.format("%06d", 100000 + i);
            // Assign random location to each driver
            String randomLocation = locationNames[random.nextInt(locationNames.length)];
            drivers.put(username, new Driver(username, password, driverNames[i], 
                                            "9876543" + String.format("%03d", i),
                                            carModels[i], carTypes[i], license, randomLocation));
        }
    }

    public boolean registerUser(String username, String password, String name, String phone) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, name, phone));
        return true;
    }

    public boolean registerDriver(String username, String password, String name, 
                                  String phone, String carModel, 
                                  String carType, String licenseNumber) {
        if (drivers.containsKey(username)) {
            return false;
        }
        // Assign random location to new driver
        java.util.Random random = new java.util.Random();
        String[] locationNames = new String[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            locationNames[i] = locations.get(i).getName();
        }
        String randomLocation = locationNames[random.nextInt(locationNames.length)];
        drivers.put(username, new Driver(username, password, name, phone, 
                                        carModel, carType, licenseNumber, randomLocation));
        return true;
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public Driver authenticateDriver(String username, String password) {
        Driver driver = drivers.get(username);
        if (driver != null && driver.getPassword().equals(password)) {
            return driver;
        }
        return null;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Location getLocationByName(String name) {
        for (Location loc : locations) {
            if (loc.getName().equals(name)) {
                return loc;
            }
        }
        return null;
    }

    public List<Driver> getAvailableDrivers(String carType) {
        List<Driver> available = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable() && driver.getCarType().equals(carType)) {
                available.add(driver);
            }
        }
        return available;
    }

    public double getDriverDistanceFromSource(Driver driver, String sourceLocation) {
        String driverLocation = driver.getCurrentLocation();
        if (driverLocation == null || driverLocation.isEmpty()) {
            return 0.0;
        }
        return getDistanceBetween(driverLocation, sourceLocation);
    }

    public double calculateEstimatedTime(double distance) {
        // Assuming average speed of 30 km/h in city traffic
        // Time in minutes = (distance / speed) * 60
        return (distance / 30.0) * 60.0;
    }

    public double getDistanceBetween(String source, String destination) {
        if (distanceMatrix.containsKey(source) && 
            distanceMatrix.get(source).containsKey(destination)) {
            return distanceMatrix.get(source).get(destination);
        }
        return 0.0;
    }

    public double calculateFare(String source, String destination, int numberOfPassengers) {
        double distance = getDistanceBetween(source, destination);
        // Base fare calculation: Rs. 10 per km + Rs. 50 base fare
        double baseFare = (distance * 10.0) + 50.0;
        return baseFare * numberOfPassengers;
    }

    public Booking createBooking(String username, String driverUsername, String carModel, 
                                String carType, String source, String destination, 
                                int numberOfPassengers) {
        Location src = getLocationByName(source);
        Location dest = getLocationByName(destination);
        if (src == null || dest == null) {
            return null;
        }

        Driver driver = drivers.get(driverUsername);
        if (driver == null || !driver.isAvailable()) {
            return null;
        }

        double totalFare = calculateFare(source, destination, numberOfPassengers);
        String bookingId = "BK" + String.format("%04d", bookingCounter++);
        
        Booking booking = new Booking(bookingId, username, driver.getName(), carModel, carType, 
                                      source, destination, numberOfPassengers, totalFare);
        bookings.add(booking);

        // Mark driver as unavailable
        driver.setAvailable(false);

        return booking;
    }

    public List<Booking> getUserBookings(String username) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUsername().equals(username)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
}




