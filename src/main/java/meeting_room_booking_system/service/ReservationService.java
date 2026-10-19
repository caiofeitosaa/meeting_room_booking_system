package meeting_room_booking_system.service;

import jakarta.transaction.Transactional;
import meeting_room_booking_system.dtos.reservation_dtos.ReservationRequest;
import meeting_room_booking_system.dtos.reservation_dtos.ReservationResponse;
import meeting_room_booking_system.dtos.reservation_dtos.SearchReservationByRoomNumberAndDateRequest;
import meeting_room_booking_system.entities.MeetingRoom;
import meeting_room_booking_system.entities.Reservation;
import meeting_room_booking_system.entities.User;
import meeting_room_booking_system.exception_handling.BadRequest400Exception;
import meeting_room_booking_system.exception_handling.Conflict409Exception;
import meeting_room_booking_system.exception_handling.NotFound404Exception;
import meeting_room_booking_system.repository.MeetingRoomRepository;
import meeting_room_booking_system.repository.ReservationRepository;
import meeting_room_booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    public ReservationService (ReservationRepository reservationRepository, UserRepository userRepository, MeetingRoomRepository meetingRoomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.meetingRoomRepository = meetingRoomRepository;
    } // end of constructor for dependency injection

    @Transactional
    public void createReservation (ReservationRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new NotFound404Exception("User not found"));

        MeetingRoom meetingRoom = meetingRoomRepository.findByRoomNumber(request.roomNumber());

        if (meetingRoom == null) {
            throw new NotFound404Exception("Meeting Room not found");
        } // end of if body

        LocalDateTime start = request.startDateTime().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime end = request.endDateTime().truncatedTo(ChronoUnit.MINUTES);

        Duration reservationDuration = Duration.between(start, end);

        LocalTime openingTime = LocalTime.of(8,0);
        LocalTime closingTime = LocalTime.of(19,0);

        if (start.isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
            throw new BadRequest400Exception("Cannot book a time in the past");
        } // end of if body

        if (start.getMinute() != 0 || start.getSecond() != 0) {
            throw new BadRequest400Exception("The reservation must start at a full hour.");
        } // end of if body

        if (end.getMinute() != 0 || end.getSecond() != 0) {
            throw new BadRequest400Exception("The reservation must end at a full hour.");
        } // end of if body

        if (start.toLocalTime().isBefore(openingTime) || start.toLocalTime().isAfter(closingTime)) {
            throw new BadRequest400Exception("Please select a starting time between 8.00 AM and 19.00 PM");
        } // end of if body

        if (end.toLocalTime().isBefore(openingTime) || end.toLocalTime().isAfter(closingTime)) {
            throw new BadRequest400Exception("Please select a ending time between 9.00 AM and 19.00 PM");
        } // end of else if body

        if (end.isBefore(start)) {
            throw new BadRequest400Exception("The end time must be after the start time");
        } // end of if body

        if (reservationDuration.compareTo(Duration.ofHours(1)) < 0) {
            throw new BadRequest400Exception("The meeting must be at least 1 hour long");
        } // end of if body

        boolean reservationClash = reservationRepository.existsByRoom_RoomNumberAndStartDateTimeLessThanAndEndDateTimeGreaterThan(request.roomNumber(), end, start);

        if (reservationClash) {
            throw new Conflict409Exception("The meeting room is already reserved during this time");
        } // end of if body

        Reservation reservation = new Reservation(start, end, user, meetingRoom);

        reservationRepository.save(reservation);
    } // end of create reservation method

    @Transactional
    public List <ReservationResponse> searchReservationByRoomNumberAndData (SearchReservationByRoomNumberAndDateRequest request) {

        // convert a LocalDate into a LocalDateTime so the JPA can query the reservations in specific date
        LocalDateTime start = request.date().atStartOfDay();
        LocalDateTime end = request.date().plusDays(1).atStartOfDay();

        List <Reservation> reservations = reservationRepository.findByRoom_RoomNumberAndStartDateTimeBetween(request.roomNumber(), end, start);
        List <ReservationResponse> responses = new ArrayList<>();

        if (reservations.isEmpty()) {
            throw new NotFound404Exception("No reservations found for this Meeting Room in this data");
        } // end of if body

        else {
            for (Reservation reservation : reservations) {
                responses.add(ReservationResponse.response(reservation));
            } // end of for body
        } // end of else body

        return responses;
    } // end of search method
} // end of class body
