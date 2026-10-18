package meeting_room_booking_system.controller;

import meeting_room_booking_system.dtos.DeleteMeetingRoomRequest;
import meeting_room_booking_system.dtos.MeetingRoomRequest;
import meeting_room_booking_system.dtos.MeetingRoomResponse;
import meeting_room_booking_system.dtos.SearchByRoomNumberRequest;
import meeting_room_booking_system.service.MeetingRoomService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Reader;

@RestController
@RequestMapping ("/rooms")
public class MeetingRoomController {
    private final MeetingRoomService meetingRoomService;

    public MeetingRoomController (MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    } // end of constructor for dependency injection

    @PostMapping ("/create")
    public void createMeetingRoom (@Valid @RequestBody MeetingRoomRequest request) {
        meetingRoomService.createMeetingRoom(request);
    } // end of create meeting room method

    @DeleteMapping ("/delete")
    public void deleteMeetingRoom (@Valid @RequestBody DeleteMeetingRoomRequest request) {
        meetingRoomService.deleteMeetingRoom(request);
    } // end of delete meeting room method

    @GetMapping ("/search")
    public MeetingRoomResponse searchByRoomNumber (@Valid @RequestBody SearchByRoomNumberRequest request) {
        return meetingRoomService.searchMeetingRoomByRoomNumber(request);
    } // end of search by room number method
} // end of class body
