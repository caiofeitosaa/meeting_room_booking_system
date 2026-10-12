package meeting_room_booking_system.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// DTOs defined as Java records use parentheses () to declare their components, instead of curly braces {} used in regular classes. The body of a record,
// if needed, is still defined using curly braces {}.
public record UserRequest (
        @NotBlank (message = "Please enter your email address.")
        @Size(max = 80, message = "The email must have a maximum of 80 characters")
        String email,

        @NotBlank (message = "Please enter your password.")
        @Size (max = 80, message = "The password must have a maximum of 80 characters")
        String password
) {}