package meeting_room_booking_system.controller;

import meeting_room_booking_system.dtos.reservation_dtos.ReservationRequest;
import meeting_room_booking_system.dtos.reservation_dtos.ReservationResponse;
import meeting_room_booking_system.dtos.reservation_dtos.SearchReservationByRoomNumberAndDateRequest;
import meeting_room_booking_system.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping ("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService) {
        this.reservationService = reservationService;
    } // end of constructor for dependency injection

    @PostMapping ("/create")
    public void createReservation (@Valid @RequestBody ReservationRequest request) {
        reservationService.createReservation(request);
    } // end of create reservation body

    @GetMapping ("/search")
    public ResponseEntity<List<ReservationResponse>> searchByRoomNumberAndDate (@RequestParam Integer roomNumber, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        SearchReservationByRoomNumberAndDateRequest request = new SearchReservationByRoomNumberAndDateRequest(roomNumber, date);
        return ResponseEntity.ok(reservationService.searchReservationByRoomNumberAndData(request));
    } // end of search by room number and date body
} // end of class body
