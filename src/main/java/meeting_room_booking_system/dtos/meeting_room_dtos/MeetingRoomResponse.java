package meeting_room_booking_system.dtos.meeting_room_dtos;

import meeting_room_booking_system.entities.MeetingRoom;

public record MeetingRoomResponse (Integer roomNumber, Integer capacity, Boolean hasProjector) {
    public static MeetingRoomResponse response (MeetingRoom meetingRoom) {
        return new MeetingRoomResponse (meetingRoom.getRoomNumber(), meetingRoom.getCapacity(), meetingRoom.getHasProjector());
    } // end of method body
} // end of record response body
