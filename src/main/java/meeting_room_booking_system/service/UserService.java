package meeting_room_booking_system.service;
import meeting_room_booking_system.models.User;
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
} // end of class body
