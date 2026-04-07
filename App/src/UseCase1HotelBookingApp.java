import java.util.*;

/**
 * MAIN CLASS - UseCase10CancellationRollback
 */
class UseCase10CancellationRollback {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);

        CancellationService cancellationService = new CancellationService();

        // Simulate confirmed bookings
        cancellationService.registerBooking("R1", "Single");
        cancellationService.registerBooking("R2", "Double");

        System.out.println("Initial Inventory:");
        inventory.printInventory();

        // Perform cancellations
        System.out.println("\nCancelling R1...");
        cancellationService.cancelBooking("R1", inventory);

        System.out.println("\nCancelling R2...");
        cancellationService.cancelBooking("R2", inventory);

        // Attempt invalid cancellation
        System.out.println("\nCancelling R3 (invalid)...");
        cancellationService.cancelBooking("R3", inventory);

        // Show updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.printInventory();

        // Show rollback history
        System.out.println("\nRollback History:");
        cancellationService.showRollbackHistory();
    }
}

/**
 * RoomInventory - manages room availability
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String roomType, int count) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + count);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void printInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * CancellationService
 */
class CancellationService {

    /** Stack that stores recently released room IDs */
    private Stack<String> releasedRoomIds;

    /** Maps reservation ID to room type */
    private Map<String, String> reservationRoomTypeMap;

    /** Initializes structures */
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking
     */
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    /**
     * Cancels a booking and restores inventory
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        // Validate reservation exists
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation. Reservation not found: " + reservationId);
            return;
        }

        // Get room type
        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to rollback stack
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventory.incrementRoom(roomType);

        // Remove reservation (prevent duplicate cancellation)
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Cancellation successful for: " + reservationId);
    }

    /**
     * Displays rollback history (LIFO order)
     */
    public void showRollbackHistory() {
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations recorded.");
            return;
        }

        while (!releasedRoomIds.isEmpty()) {
            System.out.println("Rolled back: " + releasedRoomIds.pop());
        }
    }
}