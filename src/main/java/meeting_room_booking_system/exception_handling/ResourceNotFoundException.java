package meeting_room_booking_system.exception_handling;

// error 400
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException (String message) {
        super(message);
    } // end of constructor
} // end of class body
