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
@Entity // is a JPA annotation that marks a Java class as an entity managed by the persistence context, allowing its objects to be stored and retrieved from a database.
@Table (name = "users") // otherwise by default it would be "user" on the database
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
