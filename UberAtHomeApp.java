import java.util.Scanner;

public class UberAtHomeApp {
    private static Scanner scanner = new Scanner(System.in);
    private static DataManager dataManager = DataManager.getInstance();
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    Welcome to UberAtHome!");
        System.out.println("========================================\n");
        
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showMainMenu();
            } else {
                running = showUserMenu();
            }
        }
        
        System.out.println("\nThank you for using UberAtHome!");
        scanner.close();
    }

    private static boolean showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. User Login");
        System.out.println("2. User Sign Up");
        System.out.println("3. Driver Registration");
        System.out.println("4. Exit");
        System.out.print("\nEnter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                handleUserLogin();
                break;
            case 2:
                handleUserSignUp();
                break;
            case 3:
                handleDriverRegistration();
                break;
            case 4:
                return false;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
        return true;
    }

    private static boolean showUserMenu() {
        System.out.println("\n--- Welcome, " + currentUser.getName() + " ---");
        System.out.println("1. Book a Ride");
        System.out.println("2. View My Bookings");
        System.out.println("3. Logout");
        System.out.print("\nEnter your choice: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                handleBooking();
                break;
            case 2:
                viewBookings();
                break;
            case 3:
                currentUser = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
        return true;
    }

    private static void handleUserLogin() {
        System.out.println("\n--- User Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Error: Username and password cannot be empty!");
            return;
        }
        
        User user = dataManager.authenticateUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("\nLogin successful! Welcome, " + user.getName() + "!");
        } else {
            System.out.println("\nError: Invalid username or password!");
        }
    }

    private static void handleUserSignUp() {
        System.out.println("\n--- User Sign Up ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || 
            phone.isEmpty()) {
            System.out.println("\nError: All fields are required!");
            return;
        }
        
        if (dataManager.registerUser(username, password, name, phone)) {
            System.out.println("\nRegistration successful! Please login.");
        } else {
            System.out.println("\nError: Username already exists!");
        }
    }

    private static void handleDriverRegistration() {
        System.out.println("\n--- Driver Registration ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Car Model: ");
        String carModel = scanner.nextLine().trim();
        System.out.println("Car Type:");
        System.out.println("1. 3-seater");
        System.out.println("2. 5-seater");
        System.out.print("Select (1 or 2): ");
        int carTypeChoice = getIntInput();
        String carType = (carTypeChoice == 1) ? "3-seater" : "5-seater";
        System.out.print("License Number: ");
        String license = scanner.nextLine().trim();
        
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || 
            phone.isEmpty() || carModel.isEmpty() || license.isEmpty()) {
            System.out.println("\nError: All fields are required!");
            return;
        }
        
        if (dataManager.registerDriver(username, password, name, phone, 
                                      carModel, carType, license)) {
            System.out.println("\nDriver registration successful!");
        } else {
            System.out.println("\nError: Username already exists!");
        }
    }

    private static void handleBooking() {
        System.out.println("\n--- Book a Ride ---");
        
        java.util.List<Location> locations = dataManager.getLocations();
        
        // Select Source
        System.out.println("\nSelect Source Location:");
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            System.out.println((i + 1) + ". " + loc.getName());
        }
        System.out.print("\nSelect source (1-" + locations.size() + "): ");
        int sourceChoice = getIntInput();
        if (sourceChoice < 1 || sourceChoice > locations.size()) {
            System.out.println("Invalid source selection!");
            return;
        }
        Location selectedSource = locations.get(sourceChoice - 1);
        
        // Select Destination
        System.out.println("\nSelect Destination Location:");
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            if (i != sourceChoice - 1) {
                System.out.println((i + 1) + ". " + loc.getName());
            }
        }
        System.out.print("\nSelect destination (1-" + locations.size() + ", cannot be same as source): ");
        int destChoice = getIntInput();
        if (destChoice < 1 || destChoice > locations.size() || destChoice == sourceChoice) {
            System.out.println("Invalid destination selection!");
            return;
        }
        Location selectedDestination = locations.get(destChoice - 1);
        
        // Calculate and display distance and fare
        double distance = dataManager.getDistanceBetween(selectedSource.getName(), 
                                                          selectedDestination.getName());
        System.out.print("\nNumber of passengers: ");
        int passengers = getIntInput();
        if (passengers < 1) {
            System.out.println("Error: Number of passengers must be at least 1!");
            return;
        }
        
        double totalFare = dataManager.calculateFare(selectedSource.getName(), 
                                                      selectedDestination.getName(), 
                                                      passengers);
        
        System.out.println("\n--- Trip Details ---");
        System.out.println("Source: " + selectedSource.getName());
        System.out.println("Destination: " + selectedDestination.getName());
        System.out.println("Distance: " + String.format("%.1f km", distance));
        System.out.println("Passengers: " + passengers);
        System.out.println("Total Fare: Rs." + String.format("%.2f", totalFare));
        System.out.print("\nDo you want to proceed with booking? (yes/no): ");
        String proceed = scanner.nextLine().trim().toLowerCase();
        
        if (!proceed.equals("yes") && !proceed.equals("y")) {
            System.out.println("Booking cancelled.");
            return;
        }
        
        // Select Car Type
        System.out.println("\nCar Type:");
        System.out.println("1. 3-seater");
        System.out.println("2. 5-seater");
        System.out.print("Select car type (1 or 2): ");
        int carTypeChoice = getIntInput();
        String carType = (carTypeChoice == 1) ? "3-seater" : "5-seater";
        
        java.util.List<Driver> availableDrivers = dataManager.getAvailableDrivers(carType);
        if (availableDrivers.isEmpty()) {
            System.out.println("\nError: No drivers available for " + carType + "!");
            return;
        }
        
        // Randomly select one driver and assign as nearest
        java.util.Random random = new java.util.Random();
        int randomIndex = random.nextInt(availableDrivers.size());
        Driver selectedDriver = availableDrivers.get(randomIndex);
        
        System.out.println("\n--- Driver Assigned ---");
        System.out.println("Nearest available driver to your source location:");
        System.out.println("Driver: " + selectedDriver.getName());
        System.out.println("Car: " + selectedDriver.getCarModel() + " (" + selectedDriver.getCarType() + ")");
        System.out.println("Phone: " + selectedDriver.getPhone());
        
        System.out.println("\n--- Final Booking Summary ---");
        System.out.println("Source: " + selectedSource.getName());
        System.out.println("Destination: " + selectedDestination.getName());
        System.out.println("Distance: " + String.format("%.1f km", distance));
        System.out.println("Driver: " + selectedDriver.getName());
        System.out.println("Car: " + selectedDriver.getCarModel() + " (" + selectedDriver.getCarType() + ")");
        System.out.println("Passengers: " + passengers);
        System.out.println("Total Fare: Rs." + String.format("%.2f", totalFare));
        System.out.print("\nConfirm booking? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            Booking booking = dataManager.createBooking(
                currentUser.getUsername(),
                selectedDriver.getUsername(),
                selectedDriver.getCarModel(),
                carType,
                selectedSource.getName(),
                selectedDestination.getName(),
                passengers
            );
            
            if (booking != null) {
                System.out.println("\n✓ Booking Confirmed!");
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("Status: " + booking.getStatus());
            } else {
                System.out.println("\nError: Failed to create booking!");
            }
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    private static void viewBookings() {
        System.out.println("\n--- My Bookings ---");
        java.util.List<Booking> bookings = dataManager.getUserBookings(currentUser.getUsername());
        
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }
        
        System.out.println("========================================");
        for (Booking booking : bookings) {
            double distance = dataManager.getDistanceBetween(booking.getSource(), 
                                                             booking.getDestination());
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Driver: " + booking.getDriverName());
            System.out.println("Car: " + booking.getCarModel() + " (" + booking.getCarType() + ")");
            System.out.println("Route: " + booking.getSource() + " -> " + booking.getDestination());
            System.out.println("Distance: " + String.format("%.1f km", distance));
            System.out.println("Passengers: " + booking.getNumberOfPassengers());
            System.out.println("Fare: Rs." + String.format("%.2f", booking.getTotalFare()));
            System.out.println("Status: " + booking.getStatus());
            System.out.println("----------------------------------------");
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }
}

