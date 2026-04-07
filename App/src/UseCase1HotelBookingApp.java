
import java.util.HashMap;
import java.util.Map;

/**
 * MAIN CLASS - UseCase4RoomSearch
 * Demonstrates Room Search & Availability Check (Read-Only)
 */
class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Create room objects (Domain Model)
        Room singleRoom = new Room("Single", 1, 250, 1500.0);
        Room doubleRoom = new Room("Double", 2, 400, 2500.0);
        Room suiteRoom = new Room("Suite", 3, 750, 5000.0);

        // Setup inventory (State Holder)
        Map<String, Integer> roomData = new HashMap<>();
        roomData.put("Single", 5);
        roomData.put("Double", 3);
        roomData.put("Suite", 2);

        RoomInventory inventory = new RoomInventory(roomData);

        // Search Service (Read-only logic)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}

/**
 * RoomSearchService - Handles read-only search functionality
 */
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("Room Search\n");

        // Single Room
        if (availability.get("Single") != null && availability.get("Single") > 0) {
            System.out.println("Single Room:");
            displayRoomDetails(singleRoom, availability.get("Single"));
        }

        // Double Room
        if (availability.get("Double") != null && availability.get("Double") > 0) {
            System.out.println("Double Room:");
            displayRoomDetails(doubleRoom, availability.get("Double"));
        }

        // Suite Room
        if (availability.get("Suite") != null && availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            displayRoomDetails(suiteRoom, availability.get("Suite"));
        }
    }

    private void displayRoomDetails(Room room, int availableCount) {
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Size: " + room.getSize() + " sqft");
        System.out.println("Price per night: " + room.getPrice());
        System.out.println("Available: " + availableCount);
        System.out.println();
    }
}

/**
 * Room - Domain Model
 */
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }
}

/**
 * RoomInventory - Holds availability state
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory(Map<String, Integer> roomAvailability) {
        this.roomAvailability = roomAvailability;
    }

    // Read-only access
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}
