package meeting_room_booking_system.repository;

import meeting_room_booking_system.entities.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository <MeetingRoom, Long> {
    MeetingRoom findByRoomNumber (Integer roomNumber); // findBy in spring returns an Entity

    Boolean existsByIdAndRoomNumber (Long id, Integer roomNumber); // existBy returns a boolean

    void deleteByIdAndRoomNumber (Long id, Integer roomNumber);
}