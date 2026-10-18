package meeting_room_booking_system.dtos.user_dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserLoginRequest (
        @NotBlank
        @Size (max = 50, message = "Your email must be maximum 50 characters long")
        @Email (message = "Please enter a valid email")
        String email,

        @NotBlank
        @Size (max = 20, message = "Your password must be maximum 20 characters long")
        String password
) {}
