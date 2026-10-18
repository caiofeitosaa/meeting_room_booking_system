package meeting_room_booking_system.exception_handling;

// error 400
public class BadRequest400Exception extends RuntimeException {
    public BadRequest400Exception(String message) {
        super(message);
    } // end of constructor
} // end of class body
