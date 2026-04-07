/**
 * ================================================================
 * MAIN CLASS - UseCase1HotelBookingApp
 * ================================================================
 *
 * Use Case 1: Application Entry & Welcome Message
 *
 * Description:
 * This class represents the entry point of the
 * Hotel Booking Management System.
 *
 * At this stage, the application:
 * - Starts execution from the main() method
 * - Displays a welcome message to the user
 * - Confirms that the system has started successfully
 *
 * No business logic, data structures, or user input
 * is implemented in this use case.
 *
 * The goal is to establish a clear and predictable
 * application startup point.
 *
 * @author Developer
 * @version 1.0
 */

class BookMyStayApp {

    /**
     * Application entry point.
     *
     * This method is the first method executed
     * when the program is launched by the JVM.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // Display welcome message
        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay Application ");
        System.out.println("=======================================");
        System.out.println("Hotel Booking System v1.0");
        System.out.println("Application started successfully!");
        System.out.println("=======================================");
    }
}