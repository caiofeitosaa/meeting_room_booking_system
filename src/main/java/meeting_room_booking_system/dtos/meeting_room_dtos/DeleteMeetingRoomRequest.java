package meeting_room_booking_system.dtos.meeting_room_dtos;

import javax.validation.constraints.NotNull;

public record DeleteMeetingRoomRequest (
        @NotNull
        Long id,

        @NotNull
        Integer roomNumber
) {}
