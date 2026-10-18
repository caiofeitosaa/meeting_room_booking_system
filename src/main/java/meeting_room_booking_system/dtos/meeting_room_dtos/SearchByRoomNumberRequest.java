package meeting_room_booking_system.dtos.meeting_room_dtos;

import javax.validation.constraints.NotNull;

public record SearchByRoomNumberRequest (
        @NotNull
        Integer roomNumber
) {}
