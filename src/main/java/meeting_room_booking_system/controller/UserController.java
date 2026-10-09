package meeting_room_booking_system.controller;
import meeting_room_booking_system.models.User;
import meeting_room_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // tells Spring to manage this JavaFX controller so it can inject the UserService below
public class UserController {
    // here we will use the controller for FXML, because this is not a REST API, so we don't need @Controller and other REST annotations
    @Autowired
    private UserService userService;

    public void handleSaveUser () {
        User user = new User("email", "password");
        userService.saveUser(user);
    } // end of save user method

    public void handleDeleteUser () {
        User user = new User("email", "password");
        userService.deleteUser(user);
    } // end of delete user method
} // end of class body
