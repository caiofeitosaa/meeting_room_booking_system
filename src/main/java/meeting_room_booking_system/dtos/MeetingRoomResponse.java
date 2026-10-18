package meeting_room_booking_system.dtos;

import meeting_room_booking_system.entities.MeetingRoom;

public record MeetingRoomResponse (Integer roomNumber, Integer capacity, Boolean hasProjector) {
    public MeetingRoomResponse response (MeetingRoom meetingRoom) {
        return new MeetingRoomResponse (meetingRoom.getRoomNumber(), meetingRoom.getCapacity(), meetingRoom.getHasProjector());
    } // end of method body
} // end of record response body
