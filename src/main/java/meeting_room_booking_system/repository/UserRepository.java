package meeting_room_booking_system.repository;

import meeting_room_booking_system.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail (String email);
} // end of user repository body
