import java.io.*;
import java.util.*;

/**
 * MAIN CLASS - UseCase12DataPersistenceRecovery
 *
 * Demonstrates persistence and recovery of room inventory.
 */
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        System.out.println("System Recovery");

        // Load existing data if present
        persistenceService.loadInventory(inventory, filePath);

        // If no data found, initialize fresh inventory
        if (inventory.isEmpty()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            inventory.addRoom("Single", 5);
            inventory.addRoom("Double", 3);
            inventory.addRoom("Suite", 2);
        }

        // Display inventory
        System.out.println("\nCurrent Inventory:");
        inventory.printInventory();

        // Save current state
        persistenceService.saveInventory(inventory, filePath);
        System.out.println("Inventory saved successfully.");
    }
}

/**
 * RoomInventory - maintains room type and availability
 */
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public void printInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * FilePersistenceService
 *
 * Handles saving and loading inventory from file.
 * Format: roomType=count
 */
class FilePersistenceService {

    /**
     * Saves room inventory state to a file.
     */
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory state from a file.
     */
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            return; // No file yet → fresh start
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length != 2) {
                    continue; // Skip corrupted lines
                }

                String roomType = parts[0];
                int count;

                try {
                    count = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    continue; // Skip invalid numbers
                }

                inventory.addRoom(roomType, count);
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}