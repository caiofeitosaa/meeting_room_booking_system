package meeting_room_booking_system.dtos.reservation_dtos;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record SearchReservationByRoomNumberAndDateRequest(
        @NotNull (message = "Please provide a room number")
        Integer roomNumber,
        @NotNull (message = "Please provide a date")
        LocalDate date
) {}
