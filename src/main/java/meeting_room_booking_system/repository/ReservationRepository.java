package meeting_room_booking_system.repository;

import meeting_room_booking_system.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ReservationRepository extends JpaRepository <Reservation, Long>{
    List <Reservation> findByRoom_RoomNumberAndStartDateTimeBetween (Integer roomNumber, LocalDateTime start, LocalDateTime end);

    boolean existsByRoom_RoomNumberAndStartDateTimeLessThanAndEndDateTimeGreaterThan (Integer roomNumber, LocalDateTime end, LocalDateTime start);
} // end of interface body
