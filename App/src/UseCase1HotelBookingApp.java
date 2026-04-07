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

// BookingHistory class
class BookingHistory {

    // List to store confirmed reservations
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add reservation to history
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// BookingReportService class
class BookingReportService {

    // Generate report
    public void generateReport(BookingHistory history) {

        System.out.println("\nBooking History Report:");

        List<Reservation> list = history.getConfirmedReservations();

        for (Reservation r : list) {
            System.out.println("Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType());
        }
    }
}

// Main class
class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vamathi", "Suite"));

        // Generate report
        reportService.generateReport(history);
    }
}