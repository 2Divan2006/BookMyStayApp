/**
 * ================================================================
 * Use Case 2: Basic Room Types & Static Availability
 * Single File Version
 * ================================================================
 */

abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

// ------------------ SingleRoom ------------------
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

// ------------------ DoubleRoom ------------------
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// ------------------ SuiteRoom ------------------
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// ------------------ Main Class ------------------
class UseCase2RoomInitialization {

    public static void main(String[] args) {

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization\n");

        // Single Room
        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        // Double Room
        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        // Suite Room
        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}