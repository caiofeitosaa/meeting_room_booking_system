package meeting_room_booking_system.service;

import meeting_room_booking_system.dtos.UserRequest;
import meeting_room_booking_system.dtos.UserResponse;
import meeting_room_booking_system.entities.User;
import meeting_room_booking_system.exception_handling.InvalidCredentialsException;
import meeting_room_booking_system.exception_handling.InvalidRequisitionException;
import meeting_room_booking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // tells Spring to manage this class as a Service component, it's mandatory!
public class UserService {

    @Autowired
    // for dependency injection, makes it easier to inject than manually with pure java, like myself did with the database connection through JDBC.
    private UserRepository userRepository;

    public void saveUser (User user) {
        userRepository.save(user);
    } // end of save user method

    public UserResponse login (UserRequest request) {
        User user = userRepository.findByEmail(request.email());

        if (user.getEmail().equals(request.email()) && user.getPassword().equals(request.password())) {
            return UserResponse.userResponse(userRepository.findByEmail(request.email()));
        } // end of if body

        else {
            throw new InvalidCredentialsException ("Please enter a valid email and password.");
        } // end of else body
    } // end of find user method
} // end of class body
