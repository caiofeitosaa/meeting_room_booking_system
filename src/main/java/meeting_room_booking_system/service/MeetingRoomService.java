package meeting_room_booking_system.service;

import jakarta.transaction.Transactional;
import meeting_room_booking_system.dtos.meeting_room_dtos.DeleteMeetingRoomRequest;
import meeting_room_booking_system.dtos.meeting_room_dtos.MeetingRoomRequest;
import meeting_room_booking_system.dtos.meeting_room_dtos.MeetingRoomResponse;
import meeting_room_booking_system.dtos.meeting_room_dtos.SearchByRoomNumberRequest;
import meeting_room_booking_system.entities.MeetingRoom;
import meeting_room_booking_system.exception_handling.BadRequest400Exception;
import meeting_room_booking_system.exception_handling.NotFound404Exception;
import meeting_room_booking_system.repository.MeetingRoomRepository;
import org.springframework.stereotype.Service;

@Service
public class MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService (MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    } // end of constructor for dependency injection

    @Transactional
    public void createMeetingRoom (MeetingRoomRequest request) {
        MeetingRoom meetingRoom = new MeetingRoom(request.roomNumber(), request.capacity(), request.hasProjector());

        meetingRoomRepository.save(meetingRoom);
    } // end of creating meeting room method

    @Transactional
    public void deleteMeetingRoom (DeleteMeetingRoomRequest request) {

        if (request.id() == null || request.roomNumber()== null) {
            throw new BadRequest400Exception("Please enter both valid ID and Room Number");
        } // end of if body

        else if (!meetingRoomRepository.existsByIdAndRoomNumber(request.id(), request.roomNumber())) {
            throw new NotFound404Exception("The Room doesn't exist");
        } // end of else if body

        else {
            meetingRoomRepository.deleteByIdAndRoomNumber(request.id(), request.roomNumber());
        } // end of else body
    } // end of delete meeting room method

    public MeetingRoomResponse searchMeetingRoomByRoomNumber (SearchByRoomNumberRequest request) {
        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomNumber(request.roomNumber());

        if (meetingRoom == null) {
            throw new NotFound404Exception("Meeting Room not found");
        } // end of if body

        return new MeetingRoomResponse(meetingRoom.getRoomNumber(), meetingRoom.getCapacity() ,meetingRoom.getHasProjector()).response(meetingRoom);
    } // end of search by number method
} // end of meeting room service body
