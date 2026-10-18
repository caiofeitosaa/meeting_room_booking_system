package meeting_room_booking_system.repository;

import meeting_room_booking_system.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository <Reservation, Long>{

} // end of interface body
