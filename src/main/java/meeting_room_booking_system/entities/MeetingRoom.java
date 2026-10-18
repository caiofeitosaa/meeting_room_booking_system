package meeting_room_booking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "meeting_room")
public class MeetingRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column (nullable = false)
    private Integer capacity;

    @Column (name = "has_projector", nullable = false)
    private Boolean hasProjector;

    public MeetingRoom (Integer roomNumber, Integer capacity, Boolean hasProjector) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
    } // end of constructor
} // end of class body
