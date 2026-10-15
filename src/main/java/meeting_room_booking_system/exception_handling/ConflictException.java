package meeting_room_booking_system.exception_handling;

// error 409
public class ConflictException extends RuntimeException {
    public ConflictException (String message) {
        super(message);
    } // end of constructor
} // end of class body
