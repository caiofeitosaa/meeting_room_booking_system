package meeting_room_booking_system.exception_handling;

// error 409
public class Conflict409Exception extends RuntimeException {
    public Conflict409Exception(String message) {
        super(message);
    } // end of constructor
} // end of class body
