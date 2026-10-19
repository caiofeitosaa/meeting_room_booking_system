package meeting_room_booking_system.dtos.meeting_room_dtos;

import javax.validation.constraints.NotNull;

public record DeleteMeetingRoomRequest (
        @NotNull (message = "Please provide a id number")
        Long id,

        @NotNull (message = "Please provide a room number")
        Integer roomNumber
) {}
