class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        // Shared resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Initialize inventory
        inventory.addRooms("Single", 5);
        inventory.addRooms("Double", 3);
        inventory.addRooms("Suite", 2);

        // Add booking requests
        bookingQueue.enqueue(new Reservation("Abhi", "Single"));
        bookingQueue.enqueue(new Reservation("Vanmathi", "Double"));
        bookingQueue.enqueue(new Reservation("Kural", "Suite"));
        bookingQueue.enqueue(new Reservation("Suba", "Single"));

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Print remaining inventory
        System.out.println("\nRemaining Inventory:");
        inventory.printInventory();
    }
}

/* =========================
   Concurrent Processor
   ========================= */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;

            // Critical Section 1: Queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.dequeue();
            }

            // Critical Section 2: Inventory update
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

/* =========================
   Reservation Model
   ========================= */
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

/* =========================
   Booking Queue
   ========================= */
class BookingRequestQueue {
    private java.util.Queue<Reservation> queue = new java.util.LinkedList<>();

    public void enqueue(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* =========================
   Room Inventory
   ========================= */
class RoomInventory {

    private java.util.Map<String, Integer> rooms = new java.util.HashMap<>();

    public void addRooms(String type, int count) {
        rooms.put(type, count);
    }

    public boolean allocate(String type) {
        int count = rooms.getOrDefault(type, 0);
        if (count > 0) {
            rooms.put(type, count - 1);
            return true;
        }
        return false;
    }

    public void printInventory() {
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

/* =========================
   Allocation Service
   ========================= */
class RoomAllocationService {

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        boolean success = inventory.allocate(reservation.getRoomType());

        if (success) {
            System.out.println(
                    "Booking confirmed for Guest: " + reservation.getGuestName() +
                            ", Room Type: " + reservation.getRoomType()
            );
        } else {
            System.out.println(
                    "Booking FAILED for Guest: " + reservation.getGuestName() +
                            ", Room Type: " + reservation.getRoomType()
            );
        }
    }
}