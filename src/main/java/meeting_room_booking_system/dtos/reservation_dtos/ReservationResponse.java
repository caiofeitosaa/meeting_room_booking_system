package meeting_room_booking_system.dtos.reservation_dtos;

import meeting_room_booking_system.entities.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse (Long id, Integer roomNumber, String userName, LocalDateTime startTime, LocalDateTime endTime) {
    public static ReservationResponse response (Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getRoom().getRoomNumber(), reservation.getUser().getName(), reservation.getStartDateTime(), reservation.getEndDateTime());
    } // end of response method
} // end of response body
