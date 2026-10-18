package meeting_room_booking_system.dtos.user_dtos;

import javax.validation.constraints.NotNull;

public record DeleteUserRequest (
        @NotNull
        Long id
) {}
