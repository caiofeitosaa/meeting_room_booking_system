package meeting_room_booking_system.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DeleteMeetingRoomRequest (
        @NotNull
        Long id,

        @NotNull
        Integer roomNumber
) {}
