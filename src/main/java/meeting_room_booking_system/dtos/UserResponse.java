package meeting_room_booking_system.dtos;

import meeting_room_booking_system.entities.User;

public record UserResponse (
        Long id,
        String name,
        String email
) {
    public static UserResponse userResponse (User user) {
        return new UserResponse (user.getId(), user.getName(), user.getEmail());
    } // end of user response method body
} // end of record body