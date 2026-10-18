package meeting_room_booking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @Column (name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "room_id", nullable = false)
    private MeetingRoom room;
} // end of class body
