package meeting_room_booking_system.service;

import jakarta.transaction.Transactional;
import meeting_room_booking_system.dtos.DeleteUserRequest;
import meeting_room_booking_system.dtos.UserLoginRequest;
import meeting_room_booking_system.dtos.UserRequest;
import meeting_room_booking_system.dtos.UserResponse;
import meeting_room_booking_system.entities.User;
import meeting_room_booking_system.exception_handling.BadRequest400Exception;
import meeting_room_booking_system.exception_handling.NotFound404Exception;
import meeting_room_booking_system.exception_handling.Unauthorized401Exception;
import meeting_room_booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service // tells Spring to manage this class as a Service component, it's mandatory!
public class UserService {
    // for dependency injection, makes it easier to inject than manually with pure java, like myself did with the database connection through JDBC.
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    } // end of constructor for dependency injection

    // REALLY IMPORTANT ANNOTATION
    // Manages database transactions declaratively, ensuring that a set of operations is executed as a single unit of work.
    // If all operations within the method or class complete without errors, Spring performs a commit (confirms the changes in the database).
    // If an unchecked exception (RuntimeException) occurs, Spring triggers a rollback (undoes all changes made during that transaction).
    @Transactional
    public void saveUser (UserRequest userRequest) {
        User user = new User(userRequest.name(), userRequest.email(), userRequest.password());
        userRepository.save(user);
    } // end of save user method

    @Transactional
    public void deleteUser (DeleteUserRequest request) {
        User user = userRepository.findById(request.id()).orElseThrow(() -> new NotFound404Exception("User not found"));

        userRepository.delete(user);
    } // end of delete user method

    @Transactional
    public UserResponse login (UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email());

        if (user == null || !user.getPassword().equals(request.password())) {
            throw new Unauthorized401Exception("Please enter a valid email and password.");
        } // end of if body

        return UserResponse.userResponse(user);
    } // end of find user method
} // end of class body
