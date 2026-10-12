package meeting_room_booking_system.controller;

import meeting_room_booking_system.dtos.UserRequest;
import meeting_room_booking_system.dtos.UserResponse;
import meeting_room_booking_system.entities.User;
import meeting_room_booking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping ("/")
    public RedirectView menu () {
        return new RedirectView("/html/sign_up_screen.html");
    } // end of menu method

    @PostMapping ("/users")
    public void createUser (@RequestBody User newUser) {
        userService.saveUser(newUser);
    } // end of save user method

    @PostMapping ("/users/login")
    public ResponseEntity <UserResponse> loginUser (@RequestBody UserRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    } // end of login method
} // end of class body
