import java.util.*;

/**
 * MAIN CLASS - UseCase10BookingCancellation
 * Demonstrates booking cancellation and rollback.
 */
class UseCase10BookingCancellation {

    /**
     * Handles booking cancellations and rollback.
     */
    static class CancellationService {

        private Stack<String> releasedRoomIds;
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationRoomTypeMap.containsKey(reservationId)) {
                System.out.println("Invalid cancellation request. Reservation not found.");
                return;
            }

            String roomType = reservationRoomTypeMap.get(reservationId);

            // Push to stack (rollback tracking)
            releasedRoomIds.push(reservationId);

            // Restore inventory
            inventory.incrementRoom(roomType);

            // Remove booking
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        public void showRollbackHistory() {
            System.out.println("\nRollback History (Most Recent First):");

            if (releasedRoomIds.isEmpty()) {
                System.out.println("No cancellations yet.");
                return;
            }

            for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
                System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
            }
        }
    }

    /**
     * Manages room inventory.
     */
    static class RoomInventory {

        private Map<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
        }

        public void addRoomType(String roomType, int count) {
            inventory.put(roomType, count);
        }

        public void incrementRoom(String roomType) {
            inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
        }

        public int getAvailableRooms(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);

        CancellationService service = new CancellationService();

        String reservationId = "Single-1";
        service.registerBooking(reservationId, "Single");

        System.out.println("Booking Cancellation");

        service.cancelBooking(reservationId, inventory);

        service.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailableRooms("Single"));
    }
}