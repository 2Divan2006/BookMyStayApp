import java.util.HashMap;
import java.util.Map;

/**
 * ================================================================
 * CLASS - RoomInventory
 * ================================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * @version 3.1
 */

class RoomInventory {

    // Stores available room count for each room type
    // Key -> Room type name
    // Value -> Available room count
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes inventory with default values
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data
     */
    private void initializeInventory() {
        roomAvailability.put("SingleRoom", 5);
        roomAvailability.put("DoubleRoom", 3);
        roomAvailability.put("SuiteRoom", 2);
    }

    /**
     * Returns current availability
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a room type
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}


/**
 * ================================================================
 * MAIN CLASS - UseCase3InventorySetup
 * ================================================================
 *
 * Demonstrates centralized inventory management
 *
 * @version 3.1
 */

class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("Hotel Inventory Status:\n");

        // Display inventory
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }

        // Example update
        System.out.println("\nUpdating SingleRoom availability to 4...\n");
        inventory.updateAvailability("SingleRoom", 4);

        // Display updated inventory
        System.out.println("Updated Inventory:\n");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}