package meeting_room_booking_system.repository;

import meeting_room_booking_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
} // end of user repository body
