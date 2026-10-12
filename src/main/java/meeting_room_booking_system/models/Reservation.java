package meeting_room_booking_system.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
} // end of class body
