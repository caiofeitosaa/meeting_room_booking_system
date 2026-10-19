package meeting_room_booking_system.dtos.reservation_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservationRequest  (
        @NotNull (message = "Please provide a room number")
        Integer roomNumber,

        @NotNull (message = "Please provide a user id")
        Long userId,

        @NotBlank (message = "Please provide a user for the reservation")
        String userName,

        @NotNull (message = "Please provide a data and start time for the meeting")
        LocalDateTime startDateTime,

        @NotNull (message = "Please provide a data and end time for the meeting")
        LocalDateTime endDateTime
) {}