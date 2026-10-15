package meeting_room_booking_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // JPA needs a no-argument constructor to fetch data from the database
@Entity (name = "users")// specifies that this class is a JPA entity and maps it to a database table (by default, a table named "user")
public class User {
    @Id // marks this field as the primary key of the database table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tells the database to automatically generate and auto-increment the ID (like MySQL's AUTO_INCREMENT)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    } // end of email and password constructor
} // end of class body
