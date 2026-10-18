package meeting_room_booking_system.dtos.meeting_room_dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record MeetingRoomRequest (
        @NotNull (message = "Please enter the number of the room.")
        @Size (min = 100, max = 300, message = "Please enter a room number between 100 and 300")
        Integer roomNumber,

        @NotNull(message = "Please enter the capacity of the room.")
        @Size (max = 70, message = "The maximum capacity on a meeting room is 70")
        Integer capacity,

        @NotNull (message = "The meeting room has a projector on it?")
        Boolean hasProjector
) {}
