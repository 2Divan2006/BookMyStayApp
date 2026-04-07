import java.util.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

// Room Inventory class
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room counts
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void reduceRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Room Allocation Service
class RoomAllocationService {

    // To prevent duplicate room IDs
    private Set<String> allocatedRoomIds;

    // RoomType -> Assigned Room IDs
    private Map<String, Set<String>> assignedRooms;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRooms = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (!inventory.isAvailable(roomType)) {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store allocation
        allocatedRoomIds.add(roomId);

        assignedRooms.putIfAbsent(roomType, new HashSet<>());
        assignedRooms.get(roomType).add(roomId);

        // Update inventory
        inventory.reduceRoom(roomType);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName() + ", Room ID: " + roomId);
    }

    // Generate unique Room ID
    private String generateRoomId(String roomType) {
        int count = assignedRooms.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + count;
    }
}

// Main Class
class UseCase6RoomAllocation {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add booking requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vamathi", "Suite"));

        // Process queue
        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            service.allocateRoom(r, inventory);
        }
    }
}