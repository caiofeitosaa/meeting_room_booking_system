module org.example.meeting_room_booking_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.meeting_room_booking_system to javafx.fxml;
    exports org.example.meeting_room_booking_system;
}