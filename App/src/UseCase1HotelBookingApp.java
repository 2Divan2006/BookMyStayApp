import java.util.*;

// AddOnService class
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// AddOnServiceManager class
class AddOnServiceManager {

    // ReservationID -> List of Services
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    // Calculate total service cost
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = servicesByReservation.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("Service: " + s.getServiceName() +
                    " | Cost: " + s.getCost());
        }
    }
}

// Main class
class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Assume reservation ID after allocation
        String reservationId = "RES-101";

        // Create services
        AddOnService s1 = new AddOnService("Breakfast", 500);
        AddOnService s2 = new AddOnService("Spa", 1500);
        AddOnService s3 = new AddOnService("Airport Pickup", 800);

        // Add services
        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);
        manager.addService(reservationId, s3);

        // Display services
        System.out.println("\nSelected Services:");
        manager.displayServices(reservationId);

        // Calculate total cost
        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("\nTotal Add-On Cost: " + totalCost);
    }
}